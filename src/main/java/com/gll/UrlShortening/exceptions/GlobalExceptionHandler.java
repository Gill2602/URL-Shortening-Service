package com.gll.UrlShortening.exceptions;

import com.gll.UrlShortening.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponse> AlreadyExistExceptionHandler(AlreadyExistException e) {
        int responseStatus = HttpStatus.BAD_REQUEST.value();

        return ResponseEntity.status(responseStatus).body(
                new ErrorResponse(e.getMessage(), responseStatus)
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> NotFoundExceptionHandler(NotFoundException e) {
        int responseStatus = HttpStatus.NOT_FOUND.value();

        return ResponseEntity.status(responseStatus).body(
                new ErrorResponse(e.getMessage(), responseStatus)
        );
    }

}
