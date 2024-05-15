package io.github.joaoadavid.API.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException(){
        super("Senha inválida");
    }
}
