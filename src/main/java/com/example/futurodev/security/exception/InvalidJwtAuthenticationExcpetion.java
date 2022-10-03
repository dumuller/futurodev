package com.example.futurodev.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationExcpetion extends AuthenticationException {
    public InvalidJwtAuthenticationExcpetion(String exception) {
        super(exception);
    }
}
