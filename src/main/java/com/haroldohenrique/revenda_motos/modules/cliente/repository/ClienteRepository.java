package com.haroldohenrique.revenda_motos.modules.cliente.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haroldohenrique.revenda_motos.modules.cliente.model.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {
    Optional<ClienteEntity> findByEmail(String email);
}
