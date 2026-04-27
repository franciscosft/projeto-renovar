import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { DeviceDTO } from '../../models/device.dto';
import { IndicatorDTO } from '../../models/indicator.dto';
import { ReadingDTO } from '../../models/reading.dto';
import { ReadingService } from '../../services/domain/reading.service';
import { IndicatorService } from '../../services/domain/indicator.service';
// import * as HighStock from 'highcharts/highstock';

declare var require: any;
// const hcharts = require('highcharts/highstock');
// require('highcharts/modules/exporting')(hcharts);
// require('highcharts/modules/export-data')(hcharts);

@Component({
  selector: 'app-reading',
  standalone: true,
  imports: [FormsModule],
  templateUrl: 'reading.html',
  styleUrl: 'reading.scss'
})
export class ReadingPage implements OnInit {
  device!: DeviceDTO;
  readings: ReadingDTO[] = [];
  selectedIndicator: IndicatorDTO | null = null;
  event = { startDate: '', endDate: '' };

  constructor(
    private router: Router,
    private readingService: ReadingService,
    private indicatorService: IndicatorService
  ) {}

  ngOnInit() {
    this.device = history.state.device;
  }

  fetchReadings() {
    if (!this.selectedIndicator) return;
    const indicatorId = +this.selectedIndicator.id;

    forkJoin({
      indicator: this.indicatorService.findById(indicatorId),
      readings: this.readingService.findByDeviceAndIndicator(
        this.device.id, indicatorId, this.event.startDate, this.event.endDate
      )
    }).subscribe({
      next: ({ indicator, readings }) => {
        this.readings = readings;
        const dataPoints = readings.map(r => [r.timestamp, r.value]);
        // this.renderChart(indicator, dataPoints);
      },
      error: () => {}
    });
  }

  // renderChart(indicator: IndicatorDTO, dataPoints: any[]): void {
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
  //     title: { text: this.device.name },
  //     subtitle: {
  //       text: `Latitude: ${this.device.coordinate.latitude}, Longitude: ${this.device.coordinate.longitude}`
  //     },
  //     navigator: { margin: 60 },
  //     yAxis: {
  //       plotLines: [{
  //         value: indicator.limit,
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
  //       name: indicator.name,
  //       data: dataPoints,
  //       pointStart: Date.UTC(2010, 0, 1),
  //       tooltip: { valueDecimals: 2 }
  //     }]
  //   } as any);
  // }

  goBack() {
    this.router.navigate(['/home']);
  }
}
