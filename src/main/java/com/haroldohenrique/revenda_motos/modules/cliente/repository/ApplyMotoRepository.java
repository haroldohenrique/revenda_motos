package com.haroldohenrique.revenda_motos.modules.cliente.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haroldohenrique.revenda_motos.modules.cliente.model.ApplyMotoEntity;

public interface ApplyMotoRepository extends JpaRepository<ApplyMotoEntity, UUID> {

}
