package com.haroldohenrique.revenda_motos.modules.cliente.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.haroldohenrique.revenda_motos.modules.loja.models.MotoEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "apply_motos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyMotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", insertable = false, updatable = false)
    private ClienteEntity clienteEntity;

    @ManyToOne
    @JoinColumn(name = "moto_id", insertable = false, updatable = false)
    private MotoEntity motoEntity;

    @Column(name = "cliente_id")
    private UUID clienteId;

    @Column(name = "moto_id")
    private UUID motoId;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
