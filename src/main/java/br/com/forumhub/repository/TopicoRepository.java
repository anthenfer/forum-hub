package br.com.forumhub.repository;

import br.com.forumhub.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloIgnoreCase(String titulo);
}