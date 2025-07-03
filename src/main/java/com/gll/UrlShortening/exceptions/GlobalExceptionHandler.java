package com.gll.UrlShortening.exceptions;

import com.gll.UrlShortening.responses.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalError(Exception e) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Something goes wrong", e, "Internal Server Error");
    }

    @ExceptionHandler({
            NoResourceFoundException.class,
            InvalidUrlException.class,
            AlreadyExistsException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST,
                "Bad request: " + e.getMessage(), e,
                "Bad Requests Error: " + e.getMessage());
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestNoMessageException(Exception e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST,
                "Bad request: the requests have something wrong", e,
                "Bad Requests Error: " + e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(Exception e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND,
                "Not found: " + e.getMessage(), e,
                "Not found Error: " + e.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message,
                                                             Exception e, String logMessage) {
        ErrorResponse response = new ErrorResponse(
                message,
                status.value(),
                LocalDateTime.now()
        );
        log.error("[{}] {} - {}", status, logMessage, e.getMessage(), e);
        return new ResponseEntity<>(response, status);
    }
}
