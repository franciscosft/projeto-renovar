import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ColetaPage } from './coleta';

@NgModule({
  declarations: [
    ColetaPage,
  ],
  imports: [
    IonicPageModule.forChild(ColetaPage),
  ],
})
export class ColetaPageModule {}
