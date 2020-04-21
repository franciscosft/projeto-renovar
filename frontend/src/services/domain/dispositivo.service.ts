import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { API_CONFIG } from "../../config/api.config";
import { DispositivoDTO } from "../../models/dispositivo.dto";
import { Observable } from "rxjs/Rx";

@Injectable()
export class DispositivoService {

    constructor(public http: HttpClient) {
    }

    findAll() : Observable<DispositivoDTO[]>{
        return this.http.get<DispositivoDTO[]>(`${API_CONFIG.baseUrl}/dispositivo/todos`);
    }
}