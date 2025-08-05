package br.com.forumhub.security;

import br.com.forumhub.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenJWT = recuperarToken(request);
        System.out.println("Token recuperado: " + tokenJWT);
        if (tokenJWT != null) {
            String subject = null;
            try {
                subject = tokenService.getSubject(tokenJWT);
                System.out.println("Subject do token: " + subject);
            } catch (Exception e) {
                System.out.println("Erro ao validar token: " + e.getMessage());
            }
            if (subject != null) {
                UserDetails usuario = usuarioRepository.findByLogin(subject);
                System.out.println("Usuário encontrado: " + (usuario != null ? usuario.getUsername() : "null"));
                if (usuario != null) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Autenticação definida para o usuário: " + usuario.getUsername());
                }
            }
        } else {
            System.out.println("Nenhum token JWT encontrado na requisição.");
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}