package com.haroldohenrique.revenda_motos.modules.loja.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.haroldohenrique.revenda_motos.exceptions.UserFoundException;
import com.haroldohenrique.revenda_motos.modules.loja.models.LojaEntity;
import com.haroldohenrique.revenda_motos.modules.loja.repositories.LojaRepository;

@Service
public class CreateLojaUseCase {
    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LojaEntity execute(LojaEntity lojaEntity) {
        this.lojaRepository.findByEmailOrUsername(lojaEntity.getEmail(), lojaEntity.getUsername())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        var password = passwordEncoder.encode(lojaEntity.getPassword());
        lojaEntity.setPassword(password);
        return this.lojaRepository.save(lojaEntity);
    }
}
