package com.haroldohenrique.revenda_motos.modules.loja.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.haroldohenrique.revenda_motos.exceptions.UserFoundException;
import com.haroldohenrique.revenda_motos.modules.loja.dto.CreateLojaDTO;
import com.haroldohenrique.revenda_motos.modules.loja.models.LojaEntity;
import com.haroldohenrique.revenda_motos.modules.loja.repositories.LojaRepository;

@Service
public class CreateLojaUseCase {
    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LojaEntity execute(CreateLojaDTO createLojaDTO) {
        this.lojaRepository.findByEmailOrUsername(createLojaDTO.getEmail(), createLojaDTO.getUsername())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        var password = passwordEncoder.encode(createLojaDTO.getPassword());
        var lojaEntity = LojaEntity.builder()
                .name(createLojaDTO.getName())
                .email(createLojaDTO.getEmail())
                .password(password)
                .cnpj(createLojaDTO.getCnpj())
                .username(createLojaDTO.getUsername())
                .build();
                
        return this.lojaRepository.save(lojaEntity);
    }
}
