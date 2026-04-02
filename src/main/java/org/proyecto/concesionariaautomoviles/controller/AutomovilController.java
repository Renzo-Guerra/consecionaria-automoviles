package org.proyecto.concesionariaautomoviles.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTOReq;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTORes;
import org.proyecto.concesionariaautomoviles.dto.AutomovilListDTORes;
import org.proyecto.concesionariaautomoviles.service.AutomovilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AutomovilListDTORes> traerTodos(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return ResponseEntity.ok(this.automovilService.traerTodos(pageNo, pageSize));
    }
    @GetMapping("/puertas/{cantPuertas}")
    public ResponseEntity<AutomovilListDTORes> traerPorCantPuertas(
            @PathVariable("cantPuertas") int cantPuertas,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return ResponseEntity.ok(this.automovilService.traerPorCantPuertas(cantPuertas, pageNo, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutomovilDTORes> traerPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.automovilService.traerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutomovilDTORes> editar(@PathVariable("id") Long id, @Valid @RequestBody AutomovilDTOReq dto){
        return ResponseEntity.ok(this.automovilService.editar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id){
        this.automovilService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
