package com.haroldohenrique.revenda_motos.modules.loja.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class AuthLojaRequestDTO {
    @Schema(example = "usuario@gmail.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Schema(example = "admin@123", requiredMode = RequiredMode.REQUIRED)
    private String password;
}
