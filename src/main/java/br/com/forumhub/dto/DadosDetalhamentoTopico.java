package br.com.forumhub.dto;

import br.com.forumhub.domain.topico.Topico;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        String dataCriacao,
        String status,
        String curso,
        DadosUsuario autor
) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao() != null ? topico.getDataCriacao().toString() : null,
                topico.getStatus().toString(),
                topico.getCurso().getNome(),
                topico.getUsuario() != null ? new DadosUsuario(topico.getUsuario()) : null
        );
    }
}
