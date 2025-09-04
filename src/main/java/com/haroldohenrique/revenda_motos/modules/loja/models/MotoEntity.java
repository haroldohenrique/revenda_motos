package com.haroldohenrique.revenda_motos.modules.loja.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "moto")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Integer ano;

    private String description;

    @ManyToOne()
    @JoinColumn(name = "loja_id", insertable = false, updatable = false)
    private LojaEntity lojaEntity;

    @Column(name = "loja_id", nullable = false)
    private UUID lojaId;

    @Enumerated(EnumType.STRING)
    private TipoMoto tipo;

    public enum TipoMoto {
        NAKED("naked"),
        CARENADA("carenada"),
        BIGTRAIL("bigtrail"),
        TRAIL("trail");

        private final String tipo;

        TipoMoto(String tipo) {
            this.tipo = tipo;
        }

        public String getTipo() {
            return tipo;
        }

        @JsonCreator
        public static TipoMoto fromString(String tipo) {
            for (TipoMoto t : TipoMoto.values()) {
                if (t.tipo.equalsIgnoreCase(tipo)) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Tipo de moto inválido: " + tipo);
        }
    }

}
