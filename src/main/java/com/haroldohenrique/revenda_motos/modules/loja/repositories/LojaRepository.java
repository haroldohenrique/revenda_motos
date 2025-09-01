package com.haroldohenrique.revenda_motos.modules.loja.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haroldohenrique.revenda_motos.modules.loja.models.LojaEntity;

public interface LojaRepository extends JpaRepository<LojaEntity, UUID> {
    Optional<LojaEntity> findByEmailOrUsername(String email, String username);
    Optional<LojaEntity> findByEmail(String email);

}
