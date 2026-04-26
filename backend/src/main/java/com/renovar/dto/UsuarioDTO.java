package com.renovar.dto;

import com.renovar.domain.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UsuarioDTO(
        @NotEmpty(message = "Preenchimento obrigatório") String nome,
        String sobreNome,
        @Email(message = "E-mail inválido") String email
) {
    public static UsuarioDTO from(Usuario usuario) {
        return new UsuarioDTO(usuario.getNome(), usuario.getSobrenome(), usuario.getEmail());
    }
}
