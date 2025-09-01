package com.haroldohenrique.revenda_motos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilterLoja securityFilterLoja;

    @Autowired
    private SecurityFilterCliente securityFilterCliente;

    private static final String[] PERMITED_ALL_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/actuator/**"
    };

    // sobrescrevendo algo já gerenciado pelo spring
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/cliente/").permitAll()
                            .requestMatchers("/cliente/auth").permitAll()
                            .requestMatchers("/loja/").permitAll()
                            .requestMatchers("/loja/auth").permitAll()
                            .requestMatchers(PERMITED_ALL_LIST).permitAll();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilterCliente, BasicAuthenticationFilter.class)
                .addFilterBefore(securityFilterLoja, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
