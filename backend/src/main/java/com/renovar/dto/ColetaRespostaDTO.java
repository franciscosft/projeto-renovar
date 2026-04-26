package com.renovar.dto;

import com.renovar.domain.Coleta;
import com.renovar.domain.Indicador;

public record ColetaRespostaDTO(
        Integer id,
        String dispositivo,
        String indicadorNome,
        Double medida,
        String unidade,
        Long data
) {
    public static ColetaRespostaDTO from(Coleta coleta) {
        Indicador indicador = coleta.getIndicador();
        return new ColetaRespostaDTO(
                coleta.getId(),
                coleta.getDispositivo().getNome(),
                indicador.getNome(),
                coleta.getMedida(),
                indicador.getUnidade(),
                coleta.getData().getTime()
        );
    }
}
