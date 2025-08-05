package br.com.forumhub.domain.curso;

import jakarta.persistence.*;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Curso() {}

    public Curso(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}