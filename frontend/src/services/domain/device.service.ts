import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../../config/api.config';
import { DeviceDTO } from '../../models/device.dto';

@Injectable({ providedIn: 'root' })
export class DeviceService {
  private headers = new HttpHeaders({ 'X-Subscription-Key': API_CONFIG.subscriptionKey });

  constructor(private http: HttpClient) {}

  findAll(): Observable<DeviceDTO[]> {
    return this.http.get<DeviceDTO[]>(`${API_CONFIG.baseUrl}/devices/all`, { headers: this.headers });
  }
}
