package com.haroldohenrique.revenda_motos.modules.loja.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haroldohenrique.revenda_motos.modules.loja.models.MotoEntity;

public interface MotoRepository extends JpaRepository<MotoEntity, UUID> {
    List<MotoEntity> findByNameContainingIgnoreCase(String tipo);
}
