package br.com.forumhub.dto;

import br.com.forumhub.domain.usuario.Usuario;

public record DadosUsuario(
        Long id,
        String nome
) {
    public DadosUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome());
    }
}