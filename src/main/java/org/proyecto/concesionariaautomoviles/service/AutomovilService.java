package org.proyecto.concesionariaautomoviles.service;

import lombok.RequiredArgsConstructor;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTOReq;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTORes;
import org.proyecto.concesionariaautomoviles.entity.Automovil;
import org.proyecto.concesionariaautomoviles.exception.CustomNotFoundException;
import org.proyecto.concesionariaautomoviles.mapper.AutomovilMapper;
import org.proyecto.concesionariaautomoviles.repository.AutomovilRepository;
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

        Automovil savedAutomovil = automovilRepository.save(newAutomovil);

        return AutomovilMapper.AutomovilToAutomovilDTORes(savedAutomovil);
    }

    public List<AutomovilDTORes> traerTodos() {
        List<Automovil> automoviles = this.automovilRepository.findAll();

        return automoviles.stream()
                .map(AutomovilMapper::AutomovilToAutomovilDTORes)
                .toList();
    }

    private Automovil traerEntidadPorId(Long id){
        if(id == null) throw new IllegalArgumentException("El id proporcionado no puede ser nulo!");

        return this.automovilRepository.findById(id)
                    .orElseThrow(() -> new CustomNotFoundException("No se encontró ningun automovil con el id " + id + "!"));
    }

    public AutomovilDTORes traerPorId(Long id) {
        Automovil automovil = this.traerEntidadPorId(id);

        return AutomovilMapper.AutomovilToAutomovilDTORes(automovil);
    }
}
