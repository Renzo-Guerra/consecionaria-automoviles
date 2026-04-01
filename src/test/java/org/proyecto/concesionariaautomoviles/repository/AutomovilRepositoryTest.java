package org.proyecto.concesionariaautomoviles.repository;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.proyecto.concesionariaautomoviles.entity.Automovil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class AutomovilRepositoryTest {
    @Autowired
    private AutomovilRepository automovilRepository;

    private Automovil automovil;

    @BeforeEach
    public void init(){
        this.automovil = Automovil.builder()
                .modelo("2017")
                .marca("ford")
                .motor("1.6L Sigma Ti-VCT")
                .color("negro")
                .patente("afr233")
                .build();
    }

    @Test
    public void automovilRepository_save_returnsCreatedObject(){
        Automovil savedAutomovil = this.automovilRepository.save(this.automovil);

        Assertions.assertThat(savedAutomovil).isNotNull();
        Assertions.assertThat(savedAutomovil.getId()).isNotNull();
    }

    @Test
    public void automovilRepository_save_ThrowsConstraintViolationException(){
        this.automovil.setPatente("45sd23s");

        Assertions.assertThatThrownBy(() -> this.automovilRepository.save(this.automovil))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void automovilRepository_findAll_returnsAllAutomoviles(){
        this.automovilRepository.save(automovil);

        List<Automovil> automoviles = this.automovilRepository.findAll();

        Assertions.assertThat(automoviles).isNotNull();
        Assertions.assertThat(automoviles).isNotEmpty();
        Assertions.assertThat(automoviles).hasSize(1);
    }

    @Test
    public void automovilRepository_findById_returnsRequestedAutomovil(){
        Automovil savedAutomovil = automovilRepository.save(automovil);
        Optional<Automovil> posibleAutomovil = automovilRepository.findById(savedAutomovil.getId());

        Assertions.assertThat(posibleAutomovil).isNotNull();
        Assertions.assertThat(posibleAutomovil).isNotEmpty();
        Assertions.assertThat(posibleAutomovil.get()).isEqualTo(savedAutomovil);
    }

    @Test
    public void automovilRepository_findById_returnsEmptyOptional(){
        Optional<Automovil> posibleAutomovil = automovilRepository.findById(1L);

        Assertions.assertThat(posibleAutomovil).isNotNull();
        Assertions.assertThat(posibleAutomovil).isEmpty();
    }

    @Test
    public void automovilRepository_edit_returnsEditedAutomovil(){
        Automovil savedAutomovil = automovilRepository.save(automovil);

        savedAutomovil.setModelo("sentra");
        savedAutomovil.setMarca("nissan");
        savedAutomovil.setMotor("1.6L 4 cilindros DOHC");
        savedAutomovil.setColor("Blanco Perla");
        savedAutomovil.setPatente("ABC123");

        Automovil editedAutomovil = automovilRepository.save(savedAutomovil);

        Assertions.assertThat(editedAutomovil).isNotNull();
        Assertions.assertThat(editedAutomovil.getModelo()).isEqualTo(savedAutomovil.getModelo());
        Assertions.assertThat(editedAutomovil.getMarca()).isEqualTo(savedAutomovil.getMarca());
        Assertions.assertThat(editedAutomovil.getMotor()).isEqualTo(savedAutomovil.getMotor());
        Assertions.assertThat(editedAutomovil.getColor()).isEqualTo(savedAutomovil.getColor());
        Assertions.assertThat(editedAutomovil.getPatente()).isEqualTo(savedAutomovil.getPatente());
    }

}
