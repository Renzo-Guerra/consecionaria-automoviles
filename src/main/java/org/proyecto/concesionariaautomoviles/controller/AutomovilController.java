package org.proyecto.concesionariaautomoviles.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTOReq;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTORes;
import org.proyecto.concesionariaautomoviles.service.AutomovilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<AutomovilDTORes>> traerTodos(){
        return ResponseEntity.ok(this.automovilService.traerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutomovilDTORes> traerPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.automovilService.traerPorId(id));
    }
}
