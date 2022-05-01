package com.cihatguven.springbootpostgresqlcruddemo.exception;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(Long id){
        super("Author id not found: "+id);
    }
}
