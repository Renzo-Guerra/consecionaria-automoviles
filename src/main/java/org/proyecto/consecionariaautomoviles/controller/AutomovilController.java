package org.proyecto.consecionariaautomoviles.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.proyecto.consecionariaautomoviles.dto.AutomovilDTOReq;
import org.proyecto.consecionariaautomoviles.dto.AutomovilDTORes;
import org.proyecto.consecionariaautomoviles.service.AutomovilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/automoviles")
@RequiredArgsConstructor
public class AutomovilController {
    private final AutomovilService automovilService;

    @PostMapping
    public ResponseEntity<AutomovilDTORes> crear(@Valid @RequestBody AutomovilDTOReq requestDto){
        return ResponseEntity
                .status(201)
                .body(this.automovilService.crear(requestDto));
    }
}
