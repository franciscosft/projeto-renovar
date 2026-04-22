import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../../config/api.config';
import { ColetaDTO } from '../../models/coleta.dto';

@Injectable({ providedIn: 'root' })
export class ColetaService {
  constructor(private http: HttpClient) {}

  findAllByDispositivo(idDispositivo: string): Observable<ColetaDTO[]> {
    return this.http.get<ColetaDTO[]>(`${API_CONFIG.baseUrl}/coletas/dispositivo/${idDispositivo}`);
  }

  findAllByDispositivoIndidicador(
    idDispositivo: string,
    idIndicador: number,
    inicio: string,
    fim: string
  ): Observable<ColetaDTO[]> {
    if (!inicio && !fim) {
      return this.http.get<ColetaDTO[]>(`${API_CONFIG.baseUrl}/coletas/${idDispositivo}/${idIndicador}`);
    }
    return this.http.get<ColetaDTO[]>(
      `${API_CONFIG.baseUrl}/coletas/intervalo/?idDispositivo=${idDispositivo}&idIndicador=${idIndicador}&dataInicio=${inicio}&dataFim=${fim}`
    );
  }
}
