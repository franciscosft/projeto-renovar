import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { DispositivoDTO } from '../../models/dispositivo.dto';
import { IndicadorDTO } from '../../models/indicador.dto';
import { ColetaDTO } from '../../models/coleta.dto';
import { ColetaService } from '../../services/domain/coleta.service';
import { IndicadorService } from '../../services/domain/indicador.service';
// import * as HighStock from 'highcharts/highstock';

declare var require: any;
// const hcharts = require('highcharts/highstock');
// require('highcharts/modules/exporting')(hcharts);
// require('highcharts/modules/export-data')(hcharts);

@Component({
  selector: 'app-coleta',
  standalone: true,
  imports: [FormsModule],
  templateUrl: 'coleta.html',
  styleUrl: 'coleta.scss'
})
export class ColetaPage implements OnInit {
  dispositivo!: DispositivoDTO;
  coletas: ColetaDTO[] = [];
  selectedIndicador: IndicadorDTO | null = null;
  event = { inicio: '', fim: '' };

  constructor(
    private router: Router,
    private coletaService: ColetaService,
    private indicadorService: IndicadorService
  ) {}

  ngOnInit() {
    this.dispositivo = history.state.dispositivo;
  }

  buscarColetas() {
    if (!this.selectedIndicador) return;
    const idIndicador = +this.selectedIndicador.id;

    forkJoin({
      indicador: this.indicadorService.findById(idIndicador),
      coletas: this.coletaService.findAllByDispositivoIndidicador(
        this.dispositivo.id, idIndicador, this.event.inicio, this.event.fim
      )
    }).subscribe({
      next: ({ indicador, coletas }) => {
        this.coletas = coletas;
        const medidas = coletas.map(c => [c.data, c.medida]);
        // this.renderizar(indicador, medidas);
      },
      error: () => {}
    });
  }

  // renderizar(indicador: IndicadorDTO, medidas: any[]): void {
  //   HighStock.setOptions({
  //     lang: {
  //       loading: 'Aguarde...',
  //       months: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
  //       weekdays: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
  //       shortMonths: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
  //       rangeSelectorFrom: 'De',
  //       rangeSelectorTo: 'Até',
  //       rangeSelectorZoom: 'Período',
  //       downloadPNG: 'Download imagem PNG',
  //       downloadJPEG: 'Download imagem JPEG',
  //       downloadPDF: 'Download documento PDF',
  //       downloadSVG: 'Download imagem SVG'
  //     }
  //   });
  //
  //   HighStock.stockChart('container', {
  //     exporting: {
  //       chartOptions: {
  //         plotOptions: {
  //           series: { dataLabels: { enabled: false } }
  //         }
  //       },
  //       buttons: {
  //         contextButton: {
  //           menuItems: ['downloadPDF', 'downloadPNG', 'downloadCSV', 'downloadXLS']
  //         }
  //       },
  //       csv: { dateFormat: '%Y-%m-%d %H:%M:%S', decimalPoint: '.' },
  //       fallbackToExportServer: false
  //     },
  //     title: { text: this.dispositivo.nome },
  //     subtitle: {
  //       text: `Latitude: ${this.dispositivo.coordenada.latitude}, Longitude: ${this.dispositivo.coordenada.longitude}`
  //     },
  //     navigator: { margin: 60 },
  //     yAxis: {
  //       plotLines: [{
  //         value: indicador.limite,
  //         color: 'red',
  //         dashStyle: 'shortdash',
  //         width: 2,
  //         label: { text: 'Limite superior' }
  //       }]
  //     },
  //     xAxis: {
  //       type: 'datetime',
  //       labels: { format: '{value: %H:%M:%S}' }
  //     },
  //     series: [{
  //       name: indicador.nome,
  //       data: medidas,
  //       pointStart: Date.UTC(2010, 0, 1),
  //       tooltip: { valueDecimals: 2 }
  //     }]
  //   } as any);
  // }

  voltar() {
    this.router.navigate(['/home']);
  }
}
