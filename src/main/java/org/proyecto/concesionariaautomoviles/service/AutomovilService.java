package org.proyecto.concesionariaautomoviles.service;

import lombok.RequiredArgsConstructor;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTOReq;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTORes;
import org.proyecto.concesionariaautomoviles.entity.Automovil;
import org.proyecto.concesionariaautomoviles.exception.CustomNotFoundException;
import org.proyecto.concesionariaautomoviles.exception.DuplicateValueException;
import org.proyecto.concesionariaautomoviles.mapper.AutomovilMapper;
import org.proyecto.concesionariaautomoviles.repository.AutomovilRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutomovilService {
    private final AutomovilRepository automovilRepository;

    @Transactional
    public AutomovilDTORes crear(AutomovilDTOReq requestDto){
        Automovil newAutomovil = AutomovilMapper.AutomovilDTOReqToAutomovil(requestDto);

        Automovil savedAutomovil = this.guardar(newAutomovil);

        return AutomovilMapper.AutomovilToAutomovilDTORes(savedAutomovil);
    }

    @Transactional(readOnly = true)
    public List<AutomovilDTORes> traerTodos() {
        List<Automovil> automoviles = this.automovilRepository.findAll();

        return automoviles.stream()
                .map(AutomovilMapper::AutomovilToAutomovilDTORes)
                .toList();
    }

    @Transactional
    public AutomovilDTORes traerPorId(Long id) {
        Automovil automovil = this.traerEntidadPorId(id);

        return AutomovilMapper.AutomovilToAutomovilDTORes(automovil);
    }

    @Transactional
    public AutomovilDTORes editar(Long id, AutomovilDTOReq dto) {
        Automovil automovil = this.traerEntidadPorId(id);

        automovil.setModelo(dto.getModelo());
        automovil.setMarca(dto.getMarca());
        automovil.setMotor(dto.getMotor());
        automovil.setColor(dto.getColor());
        automovil.setPatente(dto.getPatente());
        automovil.setCantPuertas(dto.getCantPuertas());

        Automovil editedAutomovil = this.guardar(automovil);

        return AutomovilMapper.AutomovilToAutomovilDTORes(editedAutomovil);
    }

    private Automovil traerEntidadPorId(Long id){
        if(id == null) throw new IllegalArgumentException("El id proporcionado no puede ser nulo!");

        return this.automovilRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("No se encontró ningun automovil con el id " + id + "!"));
    }

    private Automovil formatValues(Automovil automovil){
        // Generalizamos/estandarizamos los valores
        automovil.setModelo(automovil.getModelo().trim().toLowerCase());
        automovil.setMarca(automovil.getMarca().trim().toLowerCase());
        automovil.setMotor(automovil.getMotor().trim().toLowerCase());
        automovil.setColor(automovil.getColor().trim().toLowerCase());
        automovil.setPatente(automovil.getPatente().trim().toUpperCase());

        return automovil;
    }

    private Automovil guardar(Automovil automovil){
        Automovil formatedAutomovil = this.formatValues(automovil);

        try{
            return this.automovilRepository.save(formatedAutomovil);
        }catch (DataIntegrityViolationException exception){
            throw new DuplicateValueException("Ya existe un automovil con la patente " + automovil.getPatente());
        }
    }

    @Transactional
    public void eliminar(Long id) {
        Automovil automovil = this.traerEntidadPorId(id);

        automovilRepository.delete(automovil);
    }

    @Transactional(readOnly = true)
    public List<AutomovilDTORes> traerPorCantPuertas(int cantPuertas) {
        List<Automovil> automoviles = this.automovilRepository.findAllByCantPuertas(cantPuertas);

        return automoviles.stream()
                .map(AutomovilMapper::AutomovilToAutomovilDTORes)
                .toList();
    }
}
