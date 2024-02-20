package com.developement.authentication.presentation.exception;

public class UserAlreadyExistsException extends RuntimeException{

        public UserAlreadyExistsException(String mensagem) {
            super(mensagem);
        }
}
