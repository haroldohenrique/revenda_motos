package com.haroldohenrique.revenda_motos.modules.cliente.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.haroldohenrique.revenda_motos.exceptions.EmailFoundException;
import com.haroldohenrique.revenda_motos.modules.cliente.dto.CreateClienteDTO;
import com.haroldohenrique.revenda_motos.modules.cliente.model.ClienteEntity;
import com.haroldohenrique.revenda_motos.modules.cliente.repository.ClienteRepository;

@Service
public class CreateClienteUseCase {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClienteEntity execute(CreateClienteDTO createClienteDTO) {
        this.clienteRepository.findByEmail(createClienteDTO.getEmail())
                .ifPresent((user) -> {
                    throw new EmailFoundException(user.getEmail());
                });
        var password = passwordEncoder.encode(createClienteDTO.getPassword());

        var clienteEntity = ClienteEntity.builder()
                .name(createClienteDTO.getName())
                .email(createClienteDTO.getEmail())
                .password(password)
                .build();

        return this.clienteRepository.save(clienteEntity);
    }
}
