package com.haroldohenrique.revenda_motos.modules.cliente.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haroldohenrique.revenda_motos.modules.cliente.model.ClienteEntity;
import com.haroldohenrique.revenda_motos.modules.cliente.repository.ClienteRepository;

@Service
public class SearchClienteUseCase {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteEntity> getAllExecute() {
        var clientes = this.clienteRepository.findAll();
        return clientes;
    }

    public ClienteEntity getByEmailExecute(String email) {
        var cliente = this.clienteRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new RuntimeException("Usuário não encontrado");
                });
        return cliente;
    }
}
