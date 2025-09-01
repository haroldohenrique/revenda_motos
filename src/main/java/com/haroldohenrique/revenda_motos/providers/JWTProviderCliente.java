package com.haroldohenrique.revenda_motos.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTProviderCliente {
    @Value("${security.token.secret.cliente}")
    private String secretKey;

    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        // retornando o token inteiro decodificado(verificado que é verdadeiro).

        try {
            var decodedToken = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return decodedToken;
        } catch (JWTVerificationException ex) {
            return null;
        }
    }

}
