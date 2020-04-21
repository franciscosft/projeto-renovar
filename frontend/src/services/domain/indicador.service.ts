import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { API_CONFIG } from "../../config/api.config";
import { Observable } from "rxjs/Rx";
import { IndicadorDTO } from "../../models/indicador.dto";

@Injectable()
export class IndicadorService {

    constructor(public http: HttpClient) {
    }
    
    findById(idIndicador : number) : Observable<IndicadorDTO>{
        return this.http.get<IndicadorDTO>(`${API_CONFIG.baseUrl}/indicadores/${idIndicador}`);
    }
}


