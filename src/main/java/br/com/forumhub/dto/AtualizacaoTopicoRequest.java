package br.com.forumhub.dto;

import jakarta.validation.constraints.NotBlank;

public class AtualizacaoTopicoRequest {

    @NotBlank
    private String titulo;

    @NotBlank
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }
}

