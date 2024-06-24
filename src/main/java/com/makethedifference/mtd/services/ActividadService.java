package com.makethedifference.mtd.services;

import com.makethedifference.mtd.domain.entity.Actividad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface ActividadService {
    Actividad createActividad(Actividad actividad);
    Actividad updateActividad(Long id, Actividad actividad);
    void deleteActividad(Long id);
    Page<Actividad> listAllActividades(Pageable pageable);
    Optional<Actividad> getActividadById(Long id);
    Actividad cambiarEstado(Long id, Actividad.Estado estado);
    Actividad asignarFecha(Long id, Date fecha);
}
