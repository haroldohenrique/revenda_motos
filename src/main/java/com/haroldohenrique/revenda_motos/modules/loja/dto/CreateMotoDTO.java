package com.haroldohenrique.revenda_motos.modules.loja.dto;

import com.haroldohenrique.revenda_motos.modules.loja.models.MotoEntity.TipoMoto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMotoDTO {
    @Schema(example = "suzuki gsx r-1000", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Schema(example = "2013", requiredMode = RequiredMode.REQUIRED)
    private Integer ano;

    @Schema(example = "carenada", requiredMode = RequiredMode.REQUIRED)
    private TipoMoto tipo;

    @Schema(example = "Suzuki GSX R-1000. Moto com 998cc, carenada e bem conservada; Com apenas 12mil km.", requiredMode = RequiredMode.REQUIRED)
    private String description;
}
