package org.proyecto.concesionariaautomoviles.repository;

import org.proyecto.concesionariaautomoviles.entity.Automovil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomovilRepository extends JpaRepository<Automovil, Long> {
}
