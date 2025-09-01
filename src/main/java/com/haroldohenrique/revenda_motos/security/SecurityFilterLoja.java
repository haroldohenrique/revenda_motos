package com.haroldohenrique.revenda_motos.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.haroldohenrique.revenda_motos.providers.JWTProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilterLoja extends OncePerRequestFilter {
    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // SecurityContextHolder.getContext().setAuthentication(null);
        if (request.getRequestURI().startsWith("/loja")) {
            // header lá da requisição = bearer token
            String header = request.getHeader("Authorization");
            System.out.println("AQUI É O HEADER ANIMAL" + header);
            if (header != null) {
                var token = this.jwtProvider.validateToken(header);
                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                // salvando o subject como loja_id;
                request.setAttribute("loja_id", token.getSubject());

                var roles = token.getClaim("roles").asList(Object.class);
                var grants = roles.stream()
                        .map(
                                role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()));

                // aqui está dizendo que o token é autenticado, passando
                // subjectToken(loja_id),credentials é a senha mas como já foi validado não
                // precisa aqui mais,e por último as roles(onde ele pode ir, exemplo:
                // [admin],[funcionário]);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),
                        null,
                        grants.toList());
                // o securitycontexholder nada mais é que fixar a autenticação para que não
                // fique re-autenticando.
                // no caso não precisa autenticar toda hora, somente a cada requisição nova.
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
