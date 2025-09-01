package com.haroldohenrique.revenda_motos.exceptions;

public class MotoNotFoundException extends RuntimeException {
    public MotoNotFoundException(){
        super("Moto not found.");
    }
}
