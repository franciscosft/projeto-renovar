import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../../config/api.config';
import { IndicatorDTO } from '../../models/indicator.dto';

@Injectable({ providedIn: 'root' })
export class IndicatorService {
  constructor(private http: HttpClient) {}

  findById(indicatorId: number): Observable<IndicatorDTO> {
    return this.http.get<IndicatorDTO>(`${API_CONFIG.baseUrl}/indicadores/${indicatorId}`);
  }
}
