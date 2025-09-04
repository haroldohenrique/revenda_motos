package com.haroldohenrique.revenda_motos.modules.loja.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import lombok.Data;


@Data
public class CreateLojaDTO {
    @Schema(example = "loja do usuario", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Schema(example = "usuarioloja", requiredMode = RequiredMode.REQUIRED)
    private String username;


    @Email(message = "O campo [email] deve conter um e-mail válido.")
    @Schema(example = "usuario@gmail.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Length(min = 6, max = 100)
    @Schema(example = "admin@123", requiredMode = RequiredMode.REQUIRED)
    private String password;

    @Length(min = 14, max = 14, message = "O campo [cnpj] deve conter exatos 14 digitos.")
    @Schema(example = "15324585000107", requiredMode = RequiredMode.REQUIRED)
    private String cnpj;

}
