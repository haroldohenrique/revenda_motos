package com.haroldohenrique.revenda_motos.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    public static String objectToJSON(Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    public static String generatedToken(UUID lojaId, String secret){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Instant expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("haroldomotos")
                .withSubject(lojaId.toString())
                .withClaim("roles", Arrays.asList("LOJA"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);
            return token;
    }
}
