package org.proyecto.concesionariaautomoviles.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTOReq;
import org.proyecto.concesionariaautomoviles.dto.AutomovilDTORes;
import org.proyecto.concesionariaautomoviles.entity.Automovil;
import org.proyecto.concesionariaautomoviles.repository.AutomovilRepository;

@ExtendWith(MockitoExtension.class)
public class AutomovilServiceTest {
    @Mock
    private AutomovilRepository automovilRepository;
    @InjectMocks
    private AutomovilService automovilService;

    private Automovil automovil;
    private AutomovilDTOReq automovilDTOReq;

    @BeforeEach
    public void init(){
        this.automovilDTOReq = AutomovilDTOReq.builder()
                .modelo("2017")
                .marca("ford")
                .motor("1.6L Sigma Ti-VCT")
                .color("negro")
                .patente("afr233")
                .build();

        this.automovil = Automovil.builder()
                .id(1L)
                .modelo(automovilDTOReq.getModelo())
                .marca(automovilDTOReq.getMarca())
                .motor(automovilDTOReq.getMotor())
                .color(automovilDTOReq.getColor())
                .patente(automovilDTOReq.getPatente())
                .build();
    }

    @Test
    public void automovilService_crear_returnsCreatedDTO(){
        Mockito.when(automovilRepository.save(Mockito.any(Automovil.class)))
                .thenReturn(automovil);

        AutomovilDTORes savedAutomovil = automovilService.crear(automovilDTOReq);

        Assertions.assertThat(savedAutomovil).isNotNull();
        Assertions.assertThat(savedAutomovil.getId()).isEqualTo(automovil.getId());

        Mockito.verify(automovilRepository, Mockito.times(1)).save(Mockito.any(Automovil.class));
    }
}
