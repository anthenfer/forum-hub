package br.com.forumhub.controller;

import br.com.forumhub.domain.curso.Curso;
import br.com.forumhub.domain.topico.Topico;
import br.com.forumhub.dto.DadosAtualizacaoTopico;
import br.com.forumhub.dto.DadosCadastroTopico;
import br.com.forumhub.dto.DadosDetalhamentoTopico;
import br.com.forumhub.exception.DuplicatedResourceException;
import br.com.forumhub.exception.ResourceNotFoundException;
import br.com.forumhub.repository.CursoRepository;
import br.com.forumhub.repository.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {
        boolean existe = topicoRepository.existsByTituloIgnoreCase(dados.titulo());
        if (existe) {
            throw new DuplicatedResourceException("Já existe um tópico com esse título.");
        }
        Curso curso = cursoRepository.findByNome(dados.nomeCurso());
        if (curso == null) {
            curso = new Curso(dados.nomeCurso());
            cursoRepository.save(curso);
        }
        Topico topico = new Topico(dados, curso);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listar(
            @PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao) {
        Page<Topico> page = topicoRepository.findAll(paginacao);
        Page<DadosDetalhamentoTopico> dtoPage = page.map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico não encontrado com o ID: " + id));
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoTopico dados) {
        Topico topico = topicoRepository.getReferenceById(id);
        if (dados.nomeCurso() != null && !dados.nomeCurso().isEmpty()) {
            Curso curso = cursoRepository.findByNome(dados.nomeCurso());
            if (curso == null) {
                curso = new Curso(dados.nomeCurso());
                cursoRepository.save(curso);
            }
            topico.setCurso(curso);
        }
        topico.atualizar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tópico não encontrado com o ID: " + id);
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}