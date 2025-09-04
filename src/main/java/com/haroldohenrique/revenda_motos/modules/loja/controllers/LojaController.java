package com.haroldohenrique.revenda_motos.modules.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haroldohenrique.revenda_motos.exceptions.UserFoundException;
import com.haroldohenrique.revenda_motos.modules.loja.dto.CreateLojaDTO;
import com.haroldohenrique.revenda_motos.modules.loja.services.CreateLojaUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/loja")
@RestController
public class LojaController {

    @Autowired
    private CreateLojaUseCase createLojaUseCase;

    @PostMapping("/")
    @Operation(summary = "Criar o perfil da loja", description ="Essa função faz a criação do perfil da loja" )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CreateLojaDTO.class))
            }),
            @ApiResponse(responseCode = "409", description = "Conflit. User already exists")
    })
    @Tag(name = "Loja", description = "Informações da loja")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateLojaDTO createLojaDTO) {
        try {
            var result = this.createLojaUseCase.execute(createLojaDTO);
            return ResponseEntity.ok().body(result);
        } catch (UserFoundException ex) {
            return ResponseEntity.status(HttpStatus.FOUND).body(ex.getMessage());
        }
    }
}
