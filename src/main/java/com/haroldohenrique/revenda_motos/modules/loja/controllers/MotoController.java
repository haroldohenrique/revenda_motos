package com.haroldohenrique.revenda_motos.modules.loja.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haroldohenrique.revenda_motos.modules.loja.dto.CreateMotoDTO;
import com.haroldohenrique.revenda_motos.modules.loja.models.MotoEntity;
import com.haroldohenrique.revenda_motos.modules.loja.services.CreateMotoUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RequestMapping("/loja/moto")
@RestController
@Tag(name = "Motos", description = "Informações das motos")
public class MotoController {

        @Autowired
        private CreateMotoUseCase createMotoUseCase;

        @PostMapping("/")
        @PreAuthorize("hasRole('LOJA')")
        @Operation(summary = "Cadastro de motos", description = "Essa função faz o cadastro de motos no banco de dados")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = MotoEntity.class))
                        })
        })
        @SecurityRequirement(name = "jwt_auth")
        public ResponseEntity<Object> create(@Valid @RequestBody CreateMotoDTO createMotoDTO, HttpServletRequest request) {
                // aqui eu to pegando o subject(loja_id) lá que ta vindo do token e passando ele
                // pra entidade

                try {
                        var lojaId = request.getAttribute("loja_id");
                        var motoEntity = MotoEntity.builder()
                                        .name(createMotoDTO.getName())
                                        .ano(createMotoDTO.getAno())
                                        .tipo(createMotoDTO.getTipo())
                                        .description(createMotoDTO.getDescription())
                                        .lojaId(UUID.fromString(lojaId.toString()))
                                        .build();

                        var result = this.createMotoUseCase.execute(motoEntity);
                        return ResponseEntity.ok().body(result);

                } catch (Exception ex) {
                        return ResponseEntity.badRequest().body(ex.getMessage());
                }
        }
}
