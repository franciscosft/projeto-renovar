import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../../config/api.config';
import { DispositivoDTO } from '../../models/dispositivo.dto';

@Injectable({ providedIn: 'root' })
export class DispositivoService {
  constructor(private http: HttpClient) {}

  findAll(): Observable<DispositivoDTO[]> {
    return this.http.get<DispositivoDTO[]>(`${API_CONFIG.baseUrl}/dispositivo/todos`);
  }
}
