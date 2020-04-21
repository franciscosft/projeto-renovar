import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { API_CONFIG } from "../../config/api.config";
import { ColetaDTO } from "../../models/coleta.dto";
import { Observable } from "rxjs/Rx";

@Injectable()
export class ColetaService {

    constructor(public http: HttpClient) {
    }

    findAllByDispositivo(idDispositivo): Observable<ColetaDTO[]> {
        return this.http.get<ColetaDTO[]>(`${API_CONFIG.baseUrl}/coletas/dispositivo/${idDispositivo}`);
    }

    findAllByDispositivoIndidicador(idDispositivo, idIndicador, inicio: string, fim: string): Observable<ColetaDTO[]> {
        console.log(inicio);
        console.log(inicio.length)
        console.log(fim);
        console.log(fim.length);
        if (inicio == "" && fim == "" ) {
            console.log("entrou");
            return this.http.get<ColetaDTO[]>(`${API_CONFIG.baseUrl}/coletas/${idDispositivo}/${idIndicador}`);
        } else {
            console.log("sucesso");
            return this.http.get<ColetaDTO[]>(`${API_CONFIG.baseUrl}/coletas/intervalo/?idDispositivo=${idDispositivo}&idIndicador=${idIndicador}&dataInicio=${inicio}&dataFim=${fim}`);
        }
    }
}