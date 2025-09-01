package com.haroldohenrique.revenda_motos.modules.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haroldohenrique.revenda_motos.exceptions.UserFoundException;
import com.haroldohenrique.revenda_motos.modules.loja.models.LojaEntity;
import com.haroldohenrique.revenda_motos.modules.loja.services.CreateLojaUseCase;

import jakarta.validation.Valid;

@RequestMapping("/loja")
@RestController
public class LojaController {

    @Autowired
    private CreateLojaUseCase createLojaUseCase;

    @RequestMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody LojaEntity lojaEntity) {
        try {
            var result = this.createLojaUseCase.execute(lojaEntity);
            return ResponseEntity.ok().body(result);
        } catch (UserFoundException ex) {
            return ResponseEntity.status(HttpStatus.FOUND).body(ex.getMessage());
        }
    }
}
