package com.haroldohenrique.revenda_motos.modules.cliente.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haroldohenrique.revenda_motos.modules.cliente.dto.AuthClienteRequestDTO;
import com.haroldohenrique.revenda_motos.modules.cliente.services.AuthClienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/cliente")
@RestController
@Tag(name = "Auth", description = "Autenticar login")
public class AuthClienteController {

    @Autowired
    private AuthClienteUseCase authClienteUseCase;

    @PostMapping("/auth")
     @Operation(summary = "Autenticar cliente", description ="Essa função faz a autenticação do cliente" )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuthClienteRequestDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @Tag(name = "Loja", description = "Informações da loja")
    public ResponseEntity<Object> create(@RequestBody AuthClienteRequestDTO authClienteRequestDTO) {
        try {
            var token = this.authClienteUseCase.execute(authClienteRequestDTO);
            return ResponseEntity.ok().body(token);

        } catch (AuthenticationException ex) {
            ex.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
