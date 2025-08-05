package br.com.forumhub.dto;

public record DadosAtualizacaoTopico(
        Long id,
        String titulo,
        String mensagem,
        String nomeCurso
) {}