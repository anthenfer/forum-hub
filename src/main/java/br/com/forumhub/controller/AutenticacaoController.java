package br.com.forumhub.controller;

import br.com.forumhub.domain.usuario.Usuario;
import br.com.forumhub.dto.DadosLogin;
import br.com.forumhub.dto.TokenDto;
import br.com.forumhub.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid DadosLogin dados) {
        UsernamePasswordAuthenticationToken login =
                new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        Authentication authentication = authenticationManager.authenticate(login);
        var usuarioAutenticado = (Usuario) authentication.getPrincipal();
        String token = tokenService.gerarToken(usuarioAutenticado);
        return ResponseEntity.ok(new TokenDto(token, "Bearer"));
    }
}

