import { Component, ViewChild, ElementRef } from '@angular/core';
import { NavController, NavParams, ModalController, IonicPage } from 'ionic-angular';
import { DispositivoDTO } from "../../models/dispositivo.dto";
import { DispositivoService } from '../../services/domain/dispositivo.service';


declare var google;

@IonicPage()
@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  @ViewChild('map') mapElement: ElementRef;
  map: any;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public dispositivoService: DispositivoService,
    public modCtrl: ModalController) {
  }

  ionViewDidLoad() {
    this.dispositivoService.findAll().subscribe(response => {
      this.initMap(response);
    },
    error => {});
  }
  
  initMap(dispositivos: DispositivoDTO[]) {
    console.log(dispositivos);

    this.map = new google.maps.Map(this.mapElement.nativeElement, {
      zoom: 18,
      center: {lat: -27.6001426, lng: -48.5182837 }
    });

    dispositivos.forEach(dispositivo => {
      console.log(dispositivo.nome);
      var marker = new google.maps.Marker({
        position: {lat: dispositivo.coordenada.latitude, lng: dispositivo.coordenada.longitude },
        map: this.map,
        title: dispositivo.nome
      });

      let that = this; 
      marker.addListener('click', function() {
        // let coletaModal = that.modCtrl.create('ColetaPage', dispositivo);
        // coletaModal.present();
        that.navCtrl.push('ColetaPage', dispositivo)
      });
    });
  }

  login() {
    // let profileModal = this.modCtrl.create('LoginPage');
    // profileModal.present();
    this.navCtrl.push('LoginPage')
  }

  registrar() {
    this.navCtrl.push('CadastroPage')
  }

}
