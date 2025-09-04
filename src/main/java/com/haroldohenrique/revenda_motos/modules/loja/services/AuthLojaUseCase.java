package com.haroldohenrique.revenda_motos.modules.loja.services;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.haroldohenrique.revenda_motos.modules.loja.dto.AuthLojaRequestDTO;
import com.haroldohenrique.revenda_motos.modules.loja.dto.AuthLojaResponseDTO;
import com.haroldohenrique.revenda_motos.modules.loja.repositories.LojaRepository;

@Service
public class AuthLojaUseCase {
    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthLojaResponseDTO execute(AuthLojaRequestDTO authLojaDTO) throws AuthenticationException {
        var loja = this.lojaRepository.findByEmail(authLojaDTO.getEmail())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("email/password incorrect");
                });
        var passwordIguais = this.passwordEncoder.matches(authLojaDTO.getPassword(), loja.getPassword());

        // se a senha não for igual ->
        if (!passwordIguais) {
            throw new AuthenticationException();
        }

        // se a senha for igual, vai gerar o token ->;
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("haroldomotos")
                .withSubject(loja.getId().toString())
                .withClaim("roles", Arrays.asList("LOJA"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        var authLojaResponse = AuthLojaResponseDTO.builder()
                .access_token(token)
                .expiresIn(expiresIn.toEpochMilli())
                .build();
        return authLojaResponse;
    }
}
