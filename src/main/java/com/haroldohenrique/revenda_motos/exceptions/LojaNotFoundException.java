package com.haroldohenrique.revenda_motos.exceptions;

public class LojaNotFoundException extends RuntimeException {
    public LojaNotFoundException() {
        super("Loja not found.");
    }
}
