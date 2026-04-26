import { Component, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { DispositivoDTO } from '../../models/dispositivo.dto';
import { DispositivoService } from '../../services/domain/dispositivo.service';

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
    private dispositivoService: DispositivoService
  ) {}

  ngAfterViewInit() {
    this.dispositivoService.findAll().subscribe({
      next: response => this.initMap(response),
      error: () => {}
    });
  }

  initMap(dispositivos: DispositivoDTO[]) {
    this.map = new google.maps.Map(this.mapElement.nativeElement, {
      center: { lat: -27.6001426, lng: -48.5182837 },
      zoom: 18,
    });

    dispositivos.forEach(dispositivo => {
      const marker = new google.maps.Marker({
        position: {
          lat: +dispositivo.coordenada.latitude,
          lng: +dispositivo.coordenada.longitude
        },
        map: this.map,
        title: dispositivo.nome
      });

      marker.addListener('click', () => {
        this.router.navigate(['/coleta'], { state: { dispositivo } });
      });
    });
  }
}
