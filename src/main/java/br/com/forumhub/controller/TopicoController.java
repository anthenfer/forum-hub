package br.com.forumhub.controller;

import br.com.forumhub.dto.AtualizacaoTopicoRequest;
import br.com.forumhub.dto.TopicoRequest;
import br.com.forumhub.dto.TopicoResponse;
import br.com.forumhub.model.Autor;
import br.com.forumhub.model.Curso;
import br.com.forumhub.model.Topico;
import br.com.forumhub.repository.TopicoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private EntityManager em;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid TopicoRequest request) {
        if (topicoRepository.existsByTituloAndMensagem(request.getTitulo(), request.getMensagem())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tópico duplicado.");
        }

        Autor autor = em.find(Autor.class, request.getAutorId());
        Curso curso = em.find(Curso.class, request.getCursoId());

        Topico topico = new Topico();
        topico.setTitulo(request.getTitulo());
        topico.setMensagem(request.getMensagem());
        topico.setAutor(autor);
        topico.setCurso(curso);

        topicoRepository.save(topico);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TopicoResponse(topico));
    }

    @GetMapping
    public List<TopicoResponse> listar() {
        return topicoRepository.findAll()
                .stream()
                .map(TopicoResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalhar(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(topico -> ResponseEntity.ok(new TopicoResponse(topico)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoRequest request) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Topico topico = optional.get();
        topico.setTitulo(request.getTitulo());
        topico.setMensagem(request.getMensagem());

        return ResponseEntity.ok(new TopicoResponse(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
