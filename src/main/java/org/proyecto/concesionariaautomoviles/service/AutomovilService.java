package org.proyecto.concesionariaautomoviles.service;

import lombok.RequiredArgsConstructor;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTOReq;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTORes;
import org.proyecto.concesionariaautomoviles.entity.Automovil;
import org.proyecto.concesionariaautomoviles.mapper.AutomovilMapper;
import org.proyecto.concesionariaautomoviles.repository.AutomovilRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
}
