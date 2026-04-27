import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../../config/api.config';
import { DeviceDTO } from '../../models/device.dto';

@Injectable({ providedIn: 'root' })
export class DeviceService {
  constructor(private http: HttpClient) {}

  findAll(): Observable<DeviceDTO[]> {
    return this.http.get<DeviceDTO[]>(`${API_CONFIG.baseUrl}/dispositivo/todos`);
  }
}
