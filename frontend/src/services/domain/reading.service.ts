import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../../config/api.config';
import { ReadingDTO } from '../../models/reading.dto';

@Injectable({ providedIn: 'root' })
export class ReadingService {
  private headers = new HttpHeaders({ 'X-Subscription-Key': API_CONFIG.subscriptionKey });

  constructor(private http: HttpClient) {}

  findByDevice(deviceId: string): Observable<ReadingDTO[]> {
    return this.http.get<ReadingDTO[]>(`${API_CONFIG.baseUrl}/reading/device/${deviceId}`, { headers: this.headers });
  }

  findByDeviceAndIndicator(
    deviceId: string,
    indicatorId: number,
    startDate: string,
    endDate: string
  ): Observable<ReadingDTO[]> {
    if (!startDate && !endDate) {
      return this.http.get<ReadingDTO[]>(`${API_CONFIG.baseUrl}/reading/${deviceId}/${indicatorId}`, { headers: this.headers });
    }
    return this.http.get<ReadingDTO[]>(
      `${API_CONFIG.baseUrl}/reading/range?deviceId=${deviceId}&indicatorId=${indicatorId}&startDate=${startDate}&endDate=${endDate}`,
      { headers: this.headers }
    );
  }
}
