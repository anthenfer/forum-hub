package br.com.forumhub.dto;

import br.com.forumhub.model.Topico;

import java.time.LocalDateTime;

public class TopicoResponse {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String status;
    private String autor;
    private String curso;

    public TopicoResponse(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.status = topico.getStatus().toString();
        this.autor = topico.getAutor().getNome();
        this.curso = topico.getCurso().getNome();
    }
}
