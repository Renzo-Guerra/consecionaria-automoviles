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
import org.proyecto.concesionariaautomoviles.exception.CustomNotFoundException;
import org.proyecto.concesionariaautomoviles.repository.AutomovilRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AutomovilServiceTest {
    @Mock
    private AutomovilRepository automovilRepository;
    @InjectMocks
    private AutomovilService automovilService;

    private Automovil automovil;
    private AutomovilDTOReq automovilDTOReq;
    private AutomovilDTORes automovilDTORes;

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

        this.automovilDTORes = AutomovilDTORes.builder()
                .id(automovil.getId())
                .modelo(automovil.getModelo())
                .marca(automovil.getMarca())
                .motor(automovil.getMotor())
                .color(automovil.getColor())
                .patente(automovil.getPatente())
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

    @Test
    public void automovilService_traerTodos_returnsAllAutomoviles(){
        Mockito.when(automovilRepository.findAll())
                .thenReturn(List.of(automovil));

        List<AutomovilDTORes> automoviles = automovilService.traerTodos();

        Assertions.assertThat(automoviles).isNotNull();
        Assertions.assertThat(automoviles).isNotEmpty();
        Assertions.assertThat(automoviles).hasSize(1);

        Mockito.verify(automovilRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void automovilService_traerTodos_returnsEmptyList(){
        Mockito.when(automovilRepository.findAll())
                .thenReturn(List.of());

        List<AutomovilDTORes> automoviles = automovilService.traerTodos();

        Assertions.assertThat(automoviles).isNotNull();
        Assertions.assertThat(automoviles).isEmpty();

        Mockito.verify(automovilRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void automovilService_traerPorId_returnsRequestedAutomovil(){
        Mockito.when(automovilRepository.findById(automovil.getId()))
                .thenReturn(Optional.of(automovil));

        AutomovilDTORes returnedAutomovil = automovilService.traerPorId(automovil.getId());

        Assertions.assertThat(returnedAutomovil).isNotNull();
        Assertions.assertThat(returnedAutomovil.getId()).isEqualTo(automovil.getId());
    }

    @Test
    public void automovilService_traerPorId_throwsIllegalArgumentException(){
        Assertions.assertThatThrownBy(() -> automovilService.traerPorId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El id proporcionado no puede ser nulo!");

        Mockito.verify(automovilRepository, Mockito.never()).findById(Mockito.any(Long.class));
    }

    @Test
    public void automovilService_traerPorId_throwsCustomNotFoundException(){
        Mockito.when(automovilRepository.findById(automovil.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> automovilService.traerPorId(automovil.getId()))
                .isInstanceOf(CustomNotFoundException.class)
                .hasMessageContaining("No se encontró ningun automovil con el id " + automovil.getId() + "!");

        Mockito.verify(automovilRepository, Mockito.times(1)).findById(automovil.getId());
    }
}
