package com.haroldohenrique.revenda_motos.modules.loja.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haroldohenrique.revenda_motos.exceptions.LojaNotFoundException;
import com.haroldohenrique.revenda_motos.modules.loja.models.MotoEntity;
import com.haroldohenrique.revenda_motos.modules.loja.repositories.LojaRepository;
import com.haroldohenrique.revenda_motos.modules.loja.repositories.MotoRepository;

@Service
public class CreateMotoUseCase {
    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private LojaRepository lojaRepository;

    //TODO entender o que ta acontecendo nesse código
    public MotoEntity execute(MotoEntity motoEntity) {
        lojaRepository.findById(motoEntity.getLojaId()).orElseThrow(()->{
            throw new LojaNotFoundException();
        });
        return this.motoRepository.save(motoEntity);
    }
}
