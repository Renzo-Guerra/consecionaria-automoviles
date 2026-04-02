package org.proyecto.concesionariaautomoviles.repository;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.proyecto.concesionariaautomoviles.entity.Automovil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
                .cantPuertas(4)
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
        Pageable pageable = PageRequest.of(0, 10);

        this.automovilRepository.save(automovil);

        Page<Automovil> automoviles = this.automovilRepository.findAll(pageable);

        Assertions.assertThat(automoviles).isNotNull();
        Assertions.assertThat(automoviles.getContent()).isNotEmpty();
        Assertions.assertThat(automoviles.getTotalElements()).isEqualTo(1);
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
        savedAutomovil.setCantPuertas(4);

        Automovil editedAutomovil = automovilRepository.save(savedAutomovil);

        Assertions.assertThat(editedAutomovil).isNotNull();
        Assertions.assertThat(editedAutomovil.getModelo()).isEqualTo(savedAutomovil.getModelo());
        Assertions.assertThat(editedAutomovil.getMarca()).isEqualTo(savedAutomovil.getMarca());
        Assertions.assertThat(editedAutomovil.getMotor()).isEqualTo(savedAutomovil.getMotor());
        Assertions.assertThat(editedAutomovil.getColor()).isEqualTo(savedAutomovil.getColor());
        Assertions.assertThat(editedAutomovil.getPatente()).isEqualTo(savedAutomovil.getPatente());
        Assertions.assertThat(editedAutomovil.getCantPuertas()).isEqualTo(savedAutomovil.getCantPuertas());

    }

    @Test
    public void automovilRepository_delete_deletesAutomovil(){
        Automovil savedAutomovil = automovilRepository.save(automovil);

        automovilRepository.delete(savedAutomovil);

        Optional<Automovil> posibleAutomovil = automovilRepository.findById(savedAutomovil.getId());

        Assertions.assertThat(posibleAutomovil).isNotNull();
        Assertions.assertThat(posibleAutomovil).isEmpty();
    }

    @Test
    public void automovilRepository_findAllByCantPuertas_returnsAutomovilesWithRequestedPuertas(){
        Pageable pageable = PageRequest.of(0, 10);

        Automovil savedAutomovil = automovilRepository.save(automovil);
        System.out.println(savedAutomovil);
        Page<Automovil> filteredAutomoviles = automovilRepository.findAllByCantPuertas(savedAutomovil.getCantPuertas(), pageable);
        System.out.println(filteredAutomoviles);

        Assertions.assertThat(filteredAutomoviles).isNotNull();
        Assertions.assertThat(filteredAutomoviles.getContent()).isNotEmpty();
        Assertions.assertThat(filteredAutomoviles.getTotalElements()).isEqualTo(1);
        Assertions.assertThat(filteredAutomoviles.getContent().getFirst()).isEqualTo(savedAutomovil);
    }

    @Test
    public void automovilRepository_findAllByCantPuertas_returnsEmptyList(){
        Pageable pageable = PageRequest.of(0, 10);

        Automovil savedAutomovil = automovilRepository.save(automovil);

        Page<Automovil> filteredAutomoviles = automovilRepository.findAllByCantPuertas(savedAutomovil.getCantPuertas() + 1, pageable);

        Assertions.assertThat(filteredAutomoviles).isNotNull();
        Assertions.assertThat(filteredAutomoviles.getContent()).isEmpty();
    }

}
