import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Chart } from 'chart.js/auto';
import { DeviceDTO } from '../../models/device.dto';
import { IndicatorDTO } from '../../models/indicator.dto';
import { ReadingDTO } from '../../models/reading.dto';
import { ReadingService } from '../../services/domain/reading.service';
import { IndicatorService } from '../../services/domain/indicator.service';

type ChartPoint = { timestamp: string | number; value: number };

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
  selectedGrouping = 'none';
  event = { startDate: '', endDate: '' };

  @ViewChild('chartCanvas') chartCanvas!: ElementRef;
  private chartInstance: Chart | null = null;
  private lastIndicator: IndicatorDTO | null = null;

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
        this.lastIndicator = indicator;
        this.renderChart(indicator, this.groupReadings(readings, this.selectedGrouping));
      },
      error: () => {}
    });
  }

  onGroupingChange() {
    if (this.readings.length > 0 && this.lastIndicator) {
      this.renderChart(this.lastIndicator, this.groupReadings(this.readings, this.selectedGrouping));
    }
  }

  private groupReadings(readings: ReadingDTO[], grouping: string): ChartPoint[] {
    if (grouping === 'none') return readings;

    const buckets = new Map<string, number[]>();

    for (const r of readings) {
      const d = new Date(r.timestamp);
      const yyyy = d.getFullYear();
      const mo = String(d.getMonth() + 1).padStart(2, '0');
      const dd = String(d.getDate()).padStart(2, '0');
      const hh = String(d.getHours()).padStart(2, '0');
      const key = grouping === 'hour' ? `${yyyy}-${mo}-${dd} ${hh}` : `${yyyy}-${mo}-${dd}`;
      if (!buckets.has(key)) buckets.set(key, []);
      buckets.get(key)!.push(r.value);
    }

    return Array.from(buckets.entries()).map(([key, values]) => ({
      timestamp: key,
      value: values.reduce((a, b) => a + b, 0) / values.length
    }));
  }

  private formatLabel(timestamp: string | number): string {
    if (this.selectedGrouping === 'none') {
      const d = new Date(timestamp);
      const dd = String(d.getDate()).padStart(2, '0');
      const mo = String(d.getMonth() + 1).padStart(2, '0');
      const hh = String(d.getHours()).padStart(2, '0');
      const min = String(d.getMinutes()).padStart(2, '0');
      const ss = String(d.getSeconds()).padStart(2, '0');
      return `${dd}/${mo} ${hh}:${min}:${ss}`;
    }
    if (this.selectedGrouping === 'hour') {
      // key format: "YYYY-MM-DD HH"
      const [datePart, hourPart] = (timestamp as string).split(' ');
      const [, mo, dd] = datePart.split('-');
      return `${dd}/${mo} ${hourPart}:00`;
    }
    // day — key format: "YYYY-MM-DD"
    const [, mo, dd] = (timestamp as string).split('-');
    return `${dd}/${mo}`;
  }

  private renderChart(indicator: IndicatorDTO, points: ChartPoint[]): void {
    const labels = points.map(p => this.formatLabel(p.timestamp));
    const values = points.map(p => p.value);
    const limitValues = points.map(() => indicator.limit);

    if (this.chartInstance) {
      this.chartInstance.data.labels = labels;
      this.chartInstance.data.datasets[0].label = indicator.name;
      this.chartInstance.data.datasets[0].data = values;
      this.chartInstance.data.datasets[1].data = limitValues;
      (this.chartInstance.options.scales!['y'] as any).title.text = indicator.unit;
      this.chartInstance.update();
      return;
    }

    this.chartInstance = new Chart(this.chartCanvas.nativeElement, {
      type: 'line',
      data: {
        labels,
        datasets: [
          {
            label: indicator.name,
            data: values,
            borderColor: '#2196f3',
            backgroundColor: 'rgba(33, 150, 243, 0.1)',
            tension: 0.3,
            pointRadius: 3,
            fill: true
          },
          {
            label: 'Limit',
            data: limitValues,
            borderColor: 'red',
            borderDash: [6, 3],
            borderWidth: 2,
            pointRadius: 0,
            fill: false
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: this.device.name
          },
          legend: { position: 'top' }
        },
        scales: {
          y: {
            title: {
              display: true,
              text: indicator.unit
            }
          }
        }
      }
    });
  }

  goBack() {
    this.router.navigate(['/home']);
  }
}