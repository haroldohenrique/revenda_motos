package com.haroldohenrique.revenda_motos.modules.loja.controllers;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haroldohenrique.revenda_motos.modules.loja.dto.AuthLojaRequestDTO;
import com.haroldohenrique.revenda_motos.modules.loja.services.AuthLojaUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/loja")
@RestController
@Tag(name = "Auth", description = "Autenticar login da loja")
public class AuthLojaController {
    @Autowired
    private AuthLojaUseCase authLojaUseCase;

    @PostMapping("/auth")
    @Operation(summary = "Autenticar loja", description ="Essa função faz a autenticação da loja" )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuthLojaRequestDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @Tag(name = "Loja", description = "Informações da loja")
    public ResponseEntity<Object> create(@RequestBody AuthLojaRequestDTO authLojaDTO) {
        try {
            var token = this.authLojaUseCase.execute(authLojaDTO);
            return ResponseEntity.ok().body(token);

        } catch (AuthenticationException ex) {
            ex.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
