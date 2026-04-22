import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../../config/api.config';
import { PostDTO } from '../../models/post.dto';

@Injectable({ providedIn: 'root' })
export class DocumentationService {
  constructor(private http: HttpClient) {}

  findDocumentation(): Observable<PostDTO> {
    return this.http.get<PostDTO>(`${API_CONFIG.wpURL}/pages/1310`);
  }
}
