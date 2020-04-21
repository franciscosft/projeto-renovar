import { Component } from '@angular/core';
import { DispositivoDTO } from "../../models/dispositivo.dto";
import { IonicPage, NavController, NavParams, ViewController, DateTime } from 'ionic-angular';
import { ColetaService } from '../../services/domain/coleta.service';
import { IndicadorService } from '../../services/domain/indicador.service';
import * as HighCharts from 'highcharts';
import * as HighStock from 'highcharts/highstock';
// import * as Datas from 'highcharts/modules/export-data.js';
import { ColetaDTO } from '../../models/coleta.dto';
import { IndicadorDTO } from '../../models/indicador.dto';

// declare var require: any;
// var hcharts = require('highcharts');
// require('highcharts/modules/exporting')(hcharts);
// require('highcharts/modules/export-data')(hcharts);

declare var require: any;
var hcharts = require('highcharts/highstock');
require('highcharts/modules/exporting')(hcharts);
require('highcharts/modules/export-data')(hcharts);

@IonicPage()
@Component({
  selector: 'page-coleta',
  templateUrl: 'coleta.html',
})
export class ColetaPage {

  dispositivo: DispositivoDTO;
  coletas: ColetaDTO[];
  indicador: IndicadorDTO;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public viewCtrl: ViewController,
    public coletaService: ColetaService,
    public indicadorService: IndicadorService) {
    this.dispositivo = navParams.data;
  }

  public event = {
    yestarday: '',
    today: ''
  }

  ionViewDidLoad() {

  }

  buscarColetas(nomeIndicador: string, idIndicador: number, inicio: string, fim: string) {
    this.indicadorService.findById(idIndicador).subscribe(response => {
      this.indicador = response;
    });
    console.log("inicio", inicio);
    console.log("fim ", fim);
    this.coletaService.findAllByDispositivoIndidicador(this.dispositivo.id, idIndicador, inicio, fim).subscribe(response => {
      console.log(response);
      this.coletas = response;

      var i = 0;
      var medidas = new Array();
      this.coletas.forEach(c => {
        const medida = c.medida;
        var data = c.data;
        medidas[i] = [data, medida];
        i++;
      });

      this.renderizar(this.indicador, medidas);

    }, error => { });
  }



  renderizar(indicador: IndicadorDTO, medidas: any[]): any {
    console.log("Limite: ", indicador.limite);

    HighStock.setOptions({
    // HighCharts.setOptions({
      lang: {
        loading: 'Aguarde...',
        months: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
        weekdays: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
        shortMonths: ['Jan', 'Feb', 'Mar', 'Abr', 'Maio', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
        exportButtonTitle: "Exportar",
        printButtonTitle: "Imprimir",
        rangeSelectorFrom: "De",
        rangeSelectorTo: "Até",
        rangeSelectorZoom: "Periodo",
        downloadPNG: 'Download imagem PNG',
        downloadJPEG: 'Download imagem JPEG',
        downloadPDF: 'Download documento PDF',
        downloadSVG: 'Download imagem SVG'
      }
    }
    );

    HighStock.stockChart('container', {
    // HighCharts.chart('container', {

      exporting: {
        chartOptions: { // specific options for the exported image
          plotOptions: {
            series: {
              dataLabels: {
                enabled: false
              }
            }
          }
        },
        buttons: {
          contextButton: {
            menuItems: ['downloadPDF', 'downloadPNG', 'downloadCSV', 'downloadXLS']
          }
        },
        csv: {
          dateFormat: '%Y-%m-%d %H:%M:%S',
          decimalPoint: '.'
        },
        fallbackToExportServer: false
      },

      title: {
        text: this.dispositivo.nome
      },

      subtitle: {
        text: `Latitude: ${this.dispositivo.coordenada.latitude}, Longitude: ${this.dispositivo.coordenada.longitude}`
      },

      navigator: {
        margin: 60
      },
      
      yAxis: {
        plotLines: [{
          value: indicador.limite,
          color: 'red',
          dashStyle: 'shortdash',
          width: 2,
          label: {
            text: 'Limite superior'
          }
        },
        ],
      },

      xAxis: {
        type: 'datetime',
        labels: {
          format: '{value: %H:%M:%S}'
        }
      },
      
      series: [{
        name: indicador.nome,
        data: medidas,
        pointStart: Date.UTC(2010, 0, 1),
        tooltip: {
          valueDecimals: 2,
        },
      }],

    });

  }


  dismiss() {
    this.viewCtrl.dismiss(this.dispositivo);
  }

}
