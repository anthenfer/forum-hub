package br.com.forumhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TopicoRequest {
    @NotBlank
    private String titulo;

    @NotBlank
    private String mensagem;

    @NotNull
    private Long autorId;

    @NotNull
    private Long cursoId;

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Long getAutorId() {
        return autorId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}

