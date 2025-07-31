package br.com.forumhub.model;

import jakarta.persistence.*;

@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(nullable = true)
    private String categoria;

    public String getNome() {
        return nome;
    }

}