package org.proyecto.consecionariaautomoviles.mapper;

import org.proyecto.consecionariaautomoviles.dto.AutomovilDTOReq;
import org.proyecto.consecionariaautomoviles.dto.AutomovilDTORes;
import org.proyecto.consecionariaautomoviles.entity.Automovil;

public class AutomovilMapper {
    public static Automovil AutomovilDTOReqToAutomovil(AutomovilDTOReq dto){
        return Automovil.builder()
                .modelo(dto.getModelo())
                .marca(dto.getMarca())
                .motor(dto.getMotor())
                .color(dto.getColor())
                .patente(dto.getPatente())
                .build();
    }

    public static AutomovilDTORes AutomovilToAutomovilDTORes(Automovil automovil){
        return AutomovilDTORes.builder()
                .id(automovil.getId())
                .modelo(automovil.getModelo())
                .marca(automovil.getMarca())
                .motor(automovil.getMotor())
                .color(automovil.getColor())
                .patente(automovil.getPatente())
                .build();
    }
}
