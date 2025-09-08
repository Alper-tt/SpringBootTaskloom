package com.taskloom.exception;

import com.taskloom.model.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), e.getStatusCode().value());
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(HttpMessageNotReadableException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 401);
        return ResponseEntity.status(401).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse("Argument not valid.", e.getStatusCode().value());
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 422);
        return ResponseEntity.status(422).body(errorResponse);
    }
}
