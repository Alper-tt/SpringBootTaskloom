package com.taskloom.exception;

import com.taskloom.model.response.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleResponseStatusException(ResponseStatusException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), e.getStatusCode().value());
        return ResponseEntity.status(e.getStatusCode()).body(errorResponseDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(HttpMessageNotReadableException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), 401);
        return ResponseEntity.status(401).body(errorResponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("Argument not valid.", e.getStatusCode().value());
        return ResponseEntity.status(e.getStatusCode()).body(errorResponseDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), 422);
        return ResponseEntity.status(422).body(errorResponseDTO);
    }
}
