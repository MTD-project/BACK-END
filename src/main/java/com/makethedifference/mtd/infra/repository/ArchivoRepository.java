package com.makethedifference.mtd.infra.repository;

import com.makethedifference.mtd.domain.entity.Actividad;
import com.makethedifference.mtd.domain.entity.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
    List<Archivo> findByActividad(Actividad actividad);
}

