package org.proyecto.concesionariaautomoviles.repository;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.proyecto.concesionariaautomoviles.entity.Automovil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

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

}
