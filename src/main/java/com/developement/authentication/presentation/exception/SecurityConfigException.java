package com.developement.authentication.presentation.exception;

public class SecurityConfigException extends RuntimeException {

    public SecurityConfigException(String message, SecurityConfigException e) {
        super(message);
    }


}
