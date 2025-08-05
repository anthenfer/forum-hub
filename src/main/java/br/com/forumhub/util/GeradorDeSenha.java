package br.com.forumhub.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorDeSenha {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode("123456");
        System.out.println("A senha criptografada para '123456' é: " + senhaCriptografada);
    }
}