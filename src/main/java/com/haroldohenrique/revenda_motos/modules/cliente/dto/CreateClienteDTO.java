package com.haroldohenrique.revenda_motos.modules.cliente.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateClienteDTO {
    @Schema(example = "usuario dos santos", requiredMode = RequiredMode.REQUIRED, description = "Nome do cliente")
    @NotBlank(message = "O campo [name] não pode estar em branco.")
    private String name;

    @Email(message = "O campo [email] deve conter um e-mail válido.")
    @Schema(example = "usuario@gmail.com", requiredMode = RequiredMode.REQUIRED, description = "Email do cliente")
    private String email;

    @Length(min = 6, max = 100, message = "a senha deve conter entre [6] e [100] caracteres")
    @Schema(example = "admin@password123", minLength = 6, maxLength = 100, requiredMode = RequiredMode.REQUIRED, description = "Senha do cliente")
    private String password;
}
