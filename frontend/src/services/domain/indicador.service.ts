import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../../config/api.config';
import { IndicadorDTO } from '../../models/indicador.dto';

@Injectable({ providedIn: 'root' })
export class IndicadorService {
  constructor(private http: HttpClient) {}

  findById(idIndicador: number): Observable<IndicadorDTO> {
    return this.http.get<IndicadorDTO>(`${API_CONFIG.baseUrl}/indicadores/${idIndicador}`);
  }
}
