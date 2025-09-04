package com.haroldohenrique.revenda_motos.modules.cliente.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haroldohenrique.revenda_motos.exceptions.UserNotFoundException;
import com.haroldohenrique.revenda_motos.modules.cliente.dto.ClienteDTO;
import com.haroldohenrique.revenda_motos.modules.cliente.repository.ClienteRepository;

@Service
public class ProfileClienteUseCase {
    // pegando o ID do cliente; buscando no banco e trazendo informações.(sem a
    // senha, pois é um DTO);

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteDTO execute(UUID clienteId) {
        var cliente = this.clienteRepository.findById(clienteId)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        var clienteDTO = ClienteDTO.builder()
                .id(cliente.getId())
                .email(cliente.getEmail())
                .name(cliente.getName())
                .build();

        return clienteDTO;

    }

}
