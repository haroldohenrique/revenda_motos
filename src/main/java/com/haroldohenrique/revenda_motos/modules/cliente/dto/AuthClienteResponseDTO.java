package com.haroldohenrique.revenda_motos.modules.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthClienteResponseDTO {
    private String access_token;
    private Long expiresIn;
}
