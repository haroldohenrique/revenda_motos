package com.haroldohenrique.revenda_motos.modules.loja.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthLojaResponseDTO {
    private String access_token;
    private Long expiresIn;
}
