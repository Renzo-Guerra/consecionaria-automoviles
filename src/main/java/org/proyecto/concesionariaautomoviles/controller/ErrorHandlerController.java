package org.proyecto.concesionariaautomoviles.controller;

import org.proyecto.concesionariaautomoviles.exception.CustomNotFoundException;
import org.proyecto.concesionariaautomoviles.exception.DuplicateValueException;
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
    public ResponseEntity<HashMap<String, String>> handlerCustomNotFoundException(CustomNotFoundException exception){
        HashMap<String, String> error = new HashMap<>();

        error.put("message", exception.getMessage());

        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(DuplicateValueException.class)
    public ResponseEntity<HashMap<String, String>> handlerDuplicateValueException(DuplicateValueException exception){
        HashMap<String, String> error = new HashMap<>();

        error.put("message", exception.getMessage());

        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<HashMap<String, String>> handlerDataIntegrityViolationException(DataIntegrityViolationException exception){
        HashMap<String, String> error = new HashMap<>();

        error.put("message", exception.getMessage());

        return ResponseEntity.status(400).body(error);
    }
}
