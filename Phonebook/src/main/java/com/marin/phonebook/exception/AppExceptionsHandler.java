package com.marin.phonebook.exception;

import com.marin.phonebook.ui.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionsHandler {

    /**
     * Handle method dedicated for handling RecordsServiceException.
     */
    @ExceptionHandler(value = {RecordsServiceException.class})
    public ResponseEntity<Object> handleRecordsServiceException(RecordsServiceException ex, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
