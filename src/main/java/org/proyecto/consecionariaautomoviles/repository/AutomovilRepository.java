package org.proyecto.consecionariaautomoviles.repository;

import org.proyecto.consecionariaautomoviles.entity.Automovil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomovilRepository extends JpaRepository<Automovil, Long> {
}
