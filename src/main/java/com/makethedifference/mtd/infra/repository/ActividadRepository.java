package com.makethedifference.mtd.infra.repository;

import com.makethedifference.mtd.domain.entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
}

