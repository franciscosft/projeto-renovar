import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ViewController } from 'ionic-angular';
import { DocumentationService } from '../../services/domain/documentation.service';
import { PostDTO } from '../../models/post.dto';
import { HomePage } from '../home/home';

@IonicPage()
@Component({
  selector: 'page-documentacao',
  templateUrl: 'documentacao.html',
})
export class DocumentacaoPage {
  
  titulo: string;
  texto: string;

  homePage : HomePage;

  constructor(public navCtrl: NavController, 
              public navParams: NavParams,
              public viewCtrl: ViewController,
              public documentationService: DocumentationService) {
  }

  ionViewDidLoad() {
     this.documentationService.findDocumentation().subscribe(res => {
      console.log(res);
      this.titulo = res.title.rendered;
      this.texto = res.content.rendered;

      document.getElementById('textoPost').innerHTML = this.texto;

      // console.log("Aqui " + this.titulo);
     });
  }

  dismiss() {
    this.navCtrl.setRoot(HomePage);
    // this.navCtrl.push('HomePage')
    // this.viewCtrl.dismiss(this);
  }

}
