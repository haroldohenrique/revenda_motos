package com.haroldohenrique.revenda_motos.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException() {
        super("Email ou usuário já existentes no banco de dados.");
    }
}
