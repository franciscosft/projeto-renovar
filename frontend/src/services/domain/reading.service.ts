import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../../config/api.config';
import { ReadingDTO } from '../../models/reading.dto';

@Injectable({ providedIn: 'root' })
export class ReadingService {
  constructor(private http: HttpClient) {}

  findByDevice(deviceId: string): Observable<ReadingDTO[]> {
    return this.http.get<ReadingDTO[]>(`${API_CONFIG.baseUrl}/coletas/dispositivo/${deviceId}`);
  }

  findByDeviceAndIndicator(
    deviceId: string,
    indicatorId: number,
    startDate: string,
    endDate: string
  ): Observable<ReadingDTO[]> {
    if (!startDate && !endDate) {
      return this.http.get<ReadingDTO[]>(`${API_CONFIG.baseUrl}/coletas/${deviceId}/${indicatorId}`);
    }
    return this.http.get<ReadingDTO[]>(
      `${API_CONFIG.baseUrl}/coletas/intervalo/?idDispositivo=${deviceId}&idIndicador=${indicatorId}&dataInicio=${startDate}&dataFim=${endDate}`
    );
  }
}
