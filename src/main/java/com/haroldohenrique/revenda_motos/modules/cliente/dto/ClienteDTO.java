package com.haroldohenrique.revenda_motos.modules.cliente.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private UUID id;

    @Schema(example = "usuario@gmail.com")
    private String email;

    @Schema(example = "usuario dos santos")
    private String name;
}
