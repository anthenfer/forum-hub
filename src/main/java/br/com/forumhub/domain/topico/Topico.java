package br.com.forumhub.domain.topico;

import br.com.forumhub.domain.curso.Curso;
import br.com.forumhub.domain.usuario.Usuario;
import br.com.forumhub.dto.DadosAtualizacaoTopico;
import br.com.forumhub.dto.DadosCadastroTopico;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;

    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.NAO_RESPONDIDO;

    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;

    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Topico() {}

    public Topico(DadosCadastroTopico dados, Curso curso) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.curso = curso;
    }

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public StatusTopico getStatus() {
        return status;
    }

    public Curso getCurso() {
        return curso;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void atualizar(DadosAtualizacaoTopico dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}