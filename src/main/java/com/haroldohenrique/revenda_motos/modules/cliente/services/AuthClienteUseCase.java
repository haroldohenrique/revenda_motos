package com.haroldohenrique.revenda_motos.modules.cliente.services;

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
import com.haroldohenrique.revenda_motos.modules.cliente.dto.AuthClienteRequestDTO;
import com.haroldohenrique.revenda_motos.modules.cliente.dto.AuthClienteResponseDTO;
import com.haroldohenrique.revenda_motos.modules.cliente.repository.ClienteRepository;

@Service
public class AuthClienteUseCase {

    @Value("${security.token.secret.cliente}")
    private String secretKey;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthClienteResponseDTO execute(AuthClienteRequestDTO authClienteDTO) throws AuthenticationException {
        var cliente = this.clienteRepository.findByEmail(authClienteDTO.email())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("email/password incorrect");
                });

        var passwordMatches = passwordEncoder.matches(authClienteDTO.password(), cliente.getPassword());
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        // se a senha for igual, gere o token ->

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant expiresIn = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create().withIssuer("haroldoCliente")
                .withSubject(cliente.getId().toString())
                .withClaim("roles", Arrays.asList("CLIENTE"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        var authClienteResponse = AuthClienteResponseDTO.builder()
                .access_token(token)
                .expiresIn(expiresIn.toEpochMilli())
                .build();
        return authClienteResponse;
    }
}
