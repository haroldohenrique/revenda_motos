package com.haroldohenrique.revenda_motos.exceptions;

public class EmailFoundException extends RuntimeException {
    private String email;

    public EmailFoundException(String email) {
        super("Já existe um usuário com o email " + email + " no banco de dados");
        this.email = email;
    }

    public String getEmail(){
        return email;
    }
}
