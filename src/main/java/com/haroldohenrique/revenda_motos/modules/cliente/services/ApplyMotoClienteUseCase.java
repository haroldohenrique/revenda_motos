package com.haroldohenrique.revenda_motos.modules.cliente.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haroldohenrique.revenda_motos.exceptions.MotoNotFoundException;
import com.haroldohenrique.revenda_motos.exceptions.UserNotFoundException;
import com.haroldohenrique.revenda_motos.modules.cliente.model.ApplyMotoEntity;
import com.haroldohenrique.revenda_motos.modules.cliente.repository.ApplyMotoRepository;
import com.haroldohenrique.revenda_motos.modules.cliente.repository.ClienteRepository;
import com.haroldohenrique.revenda_motos.modules.loja.repositories.MotoRepository;

@Service
public class ApplyMotoClienteUseCase {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private ApplyMotoRepository applyMotoRepository;

    public ApplyMotoEntity execute(UUID clienteId, UUID motoId) {
        this.clienteRepository.findById(clienteId)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        this.motoRepository.findById(motoId)
                .orElseThrow(() -> {
                    throw new MotoNotFoundException();
                });

        var applyMoto = ApplyMotoEntity.builder()
                .clienteId(clienteId)
                .motoId(motoId)
                .build();
        applyMoto = applyMotoRepository.save(applyMoto);
        return applyMoto;
    }
}
