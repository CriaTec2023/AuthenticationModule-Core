package com.developement.authentication.presentation.exception;

public class NoUserFindOnSession extends RuntimeException{

    public NoUserFindOnSession(String mensagem) {
        super(mensagem);
    }
}
