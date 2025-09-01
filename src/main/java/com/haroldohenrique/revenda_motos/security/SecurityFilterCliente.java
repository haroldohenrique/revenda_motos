package com.haroldohenrique.revenda_motos.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.haroldohenrique.revenda_motos.providers.JWTProviderCliente;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilterCliente extends OncePerRequestFilter {
    @Autowired
    private JWTProviderCliente jwtProviderCliente;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //SecurityContextHolder.getContext().setAuthentication(null);

        if (request.getRequestURI().startsWith("/cliente")) {
            String header = request.getHeader("Authorization");
            if (header != null) {
                var token = this.jwtProviderCliente.validateToken(header);
                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                request.setAttribute("cliente_id", token.getSubject());

                var roles = token.getClaim("roles").asList(Object.class);
                var grants = roles.stream()
                        .map(
                                role -> new SimpleGrantedAuthority("ROLE_"+role.toString().toUpperCase()));

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),
                        null, grants.toList());
                SecurityContextHolder.getContext().setAuthentication(auth);

            }

        }

        filterChain.doFilter(request, response);
    }

}
