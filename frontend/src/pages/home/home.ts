import { Component, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { DeviceDTO } from '../../models/device.dto';
import { DeviceService } from '../../services/domain/device.service';

declare var google: any;

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: 'home.html',
  imports: [
  ],
  styleUrl: 'home.scss'
})
export class HomePage implements AfterViewInit {
  @ViewChild('map') mapElement!: ElementRef;
  map: any;

  constructor(
    private router: Router,
    private deviceService: DeviceService
  ) {}

  ngAfterViewInit() {
    this.deviceService.findAll().subscribe({
      next: response => this.initMap(response),
      error: () => {}
    });
  }

  initMap(devices: DeviceDTO[]) {
    this.map = new google.maps.Map(this.mapElement.nativeElement, {
      center: { lat: -27.6001426, lng: -48.5182837 },
      zoom: 18,
    });

    devices.forEach(device => {
      const marker = new google.maps.Marker({
        position: {
          lat: +device.coordinate.latitude,
          lng: +device.coordinate.longitude
        },
        map: this.map,
        title: device.name
      });

      marker.addListener('click', () => {
        this.router.navigate(['/reading'], { state: { device } });
      });
    });
  }
}
