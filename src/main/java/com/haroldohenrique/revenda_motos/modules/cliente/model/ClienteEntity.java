package com.haroldohenrique.revenda_motos.modules.cliente.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity(name = "cliente")
@Data
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Schema(example = "usuario dos santos", requiredMode = RequiredMode.REQUIRED, description = "Nome do cliente")
    private String name;

    @Email(message = "O campo [email] deve conter um e-mail válido.")
    @Schema(example = "usuario@gmail.com", requiredMode = RequiredMode.REQUIRED, description = "Email do cliente")
    private String email;

    @Length(min = 6, max = 100, message = "a senha deve conter entre [6] e [100] caracteres")
    @NotBlank
    @Schema(example = "admin@password123",minLength = 6, maxLength = 100 ,requiredMode = RequiredMode.REQUIRED, description = "Senha do cliente")
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
