package com.rishi.ticketmasterchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ResponseHandler {

    @ExceptionHandler(value = HttpClientErrorException.class)
    protected ResponseEntity<ErrorResponse> handleRestClientException(HttpClientErrorException ex) {
        return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
                .errorCode(ex.getStatusCode().toString())
                .errorReason(ex.getMessage())
                .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = DataNotAvailableException.class)
    protected ResponseEntity<ErrorResponse> handleUnAvailableDataException(DataNotAvailableException ex) {
        return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.toString())
                .errorReason(ex.getMessage())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

}
