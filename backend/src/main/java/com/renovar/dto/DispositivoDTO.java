package com.renovar.dto;

import java.util.List;

import com.renovar.domain.Coordenada;
import com.renovar.domain.Dispositivo;
import com.renovar.domain.Indicador;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DispositivoDTO(
        Integer id,
        @NotEmpty(message = "Preencha o nome do sensor para adicionar") String nome,
        String codigoRastreio,
        Coordenada coordenada,
        @NotNull(message = "Preencha o identificador do usuário que tem o sensor") Integer usuarioId,
        String usuario,
        List<Indicador> indicadores
) {
    public static DispositivoDTO from(Dispositivo dispositivo) {
        return new DispositivoDTO(
                dispositivo.getId(),
                dispositivo.getNome(),
                dispositivo.getCodigoRastreio(),
                new Coordenada(dispositivo.getLatitude(), dispositivo.getLongitude()),
                dispositivo.getUsuario().getId(),
                dispositivo.getUsuario().getNome(),
                dispositivo.getIndicadores()
        );
    }
}
