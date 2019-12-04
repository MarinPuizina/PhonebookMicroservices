package com.marin.phonebook.exception;

public class RecordsServiceException extends RuntimeException {

    private static final long serialVersionUID = -7204852354063744680L;

    public RecordsServiceException(String message) {
        super(message);
    }
}
