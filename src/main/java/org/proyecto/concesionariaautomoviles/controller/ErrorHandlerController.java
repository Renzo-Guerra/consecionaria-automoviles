package org.proyecto.concesionariaautomoviles.controller;

import org.proyecto.concesionariaautomoviles.exception.CustomNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, List<String>>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        HashMap<String, List<String>> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    String field = fieldError.getField();
                    String errMessage = fieldError.getDefaultMessage();

                    errors.computeIfAbsent(field, k -> new ArrayList<>()).add(errMessage);
                });

        return ResponseEntity.status(400).body(errors);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<String> handlerCustomNotFoundException(CustomNotFoundException exception){
        return ResponseEntity.status(404).body(exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handlerDataIntegrityViolationException(DataIntegrityViolationException exception){
        return ResponseEntity.status(400).body(exception.getMessage());
    }
}
