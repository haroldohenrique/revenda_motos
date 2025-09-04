package com.haroldohenrique.revenda_motos.modules.cliente.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haroldohenrique.revenda_motos.modules.loja.models.MotoEntity;
import com.haroldohenrique.revenda_motos.modules.loja.repositories.MotoRepository;

@Service
public class ListAllMotosFilterUseCase{

    @Autowired
    private MotoRepository motoRepository;

    public List<MotoEntity> execute(String filter){
        return this.motoRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}
