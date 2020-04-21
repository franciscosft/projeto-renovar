import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule } from '@angular/common/http';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';

import { MyApp } from './app.component';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { DispositivoService } from '../services/domain/dispositivo.service';
import { ErrorInterceptorProvider } from '../interceptors/error-interceptor';
import { ColetaService } from '../services/domain/coleta.service';
import { IndicadorService } from '../services/domain/indicador.service';
import { DocumentationService } from '../services/domain/documentation.service';

@NgModule({
  declarations: [
    MyApp,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    IonicModule.forRoot(MyApp, {
      monthNames: ['janeiro', 'fevereiro', 'março', 'abril', 'maio', 'junho', 'agosto', 'setembro', 'outubro', 'novembro', 'dezembro'],
      monthShortNames: ['jan', 'fev', 'mar', 'abr', 'mai', 'jun', 'ago', 'set', 'out', 'nov', 'dez'],
      dayNames: ['domingo', 'segunda-feira', 'terça-feira', 'quarta-feira', 'quinta-feira', 'sexta-feira', 'sabado'],
      dayShortNames: ['dom', 'seg', 'ter', 'qua', 'qui', 'sex', 'sab'],
    })
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    DispositivoService,
    ColetaService,
    IndicadorService,
    DocumentationService,
    ErrorInterceptorProvider,
  ]
})
export class AppModule {}
