import { IndicadorDTO } from "./indicador.dto";

export interface DispositivoDTO {
     id : string;
     nome : string;
     codigoRastreio: string;
     coordenada: {
         latitude : string;
         longitude: string;
     }
     indicadores: IndicadorDTO[];
}