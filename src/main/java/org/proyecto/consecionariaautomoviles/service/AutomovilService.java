package org.proyecto.consecionariaautomoviles.service;

import lombok.RequiredArgsConstructor;
import org.proyecto.consecionariaautomoviles.dto.AutomovilDTOReq;
import org.proyecto.consecionariaautomoviles.dto.AutomovilDTORes;
import org.proyecto.consecionariaautomoviles.entity.Automovil;
import org.proyecto.consecionariaautomoviles.mapper.AutomovilMapper;
import org.proyecto.consecionariaautomoviles.repository.AutomovilRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
