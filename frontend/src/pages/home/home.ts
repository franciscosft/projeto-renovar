import { Component, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import * as L from 'leaflet';
import { DeviceDTO } from '../../models/device.dto';
import { DeviceService } from '../../services/domain/device.service';
import { AuthService } from '../../services/auth.service';

// Fix broken default marker icons when bundled by Angular
const iconDefault = L.icon({
  iconUrl: 'assets/marker-icon.png',
  iconRetinaUrl: 'assets/marker-icon-2x.png',
  shadowUrl: 'assets/marker-shadow.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41],
});
L.Marker.prototype.options.icon = iconDefault;

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: 'home.html',
  imports: [],
  styleUrl: 'home.scss'
})
export class HomePage implements AfterViewInit {
  @ViewChild('map') mapElement!: ElementRef;

  constructor(
    private router: Router,
    private deviceService: DeviceService,
    private authService: AuthService
  ) {}

  ngAfterViewInit() {
    const map = L.map(this.mapElement.nativeElement).setView(
      [-27.6001426, -48.5182837],
      13
    );

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(map);

    this.deviceService.findAll().subscribe({
      next: devices => this.addMarkers(map, devices),
      error: () => {}
    });
  }

  private addMarkers(map: L.Map, devices: DeviceDTO[]) {
    devices.forEach(device => {
      const lat = +device.coordinate.latitude;
      const lng = +device.coordinate.longitude;

      const marker = L.marker([lat, lng])
        .addTo(map)
        .bindPopup(device.name);

      marker.on('click', () => {
        this.router.navigate(['/reading'], { state: { device } });
      });
    });
  }

  navigateToDeviceRegistration() {
    this.router.navigate(['/devices/new']);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}