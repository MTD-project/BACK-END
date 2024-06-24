package com.makethedifference.mtd.services.impl;

import com.makethedifference.mtd.domain.entity.Actividad;
import com.makethedifference.mtd.infra.repository.ActividadRepository;
import com.makethedifference.mtd.services.ActividadService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActividadServiceImpl implements ActividadService {
    private final ActividadRepository actividadRepository;

    @Override
    public Actividad createActividad(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    @Override
    public Actividad updateActividad(Long id, Actividad actividad) {
        Actividad existingActividad = actividadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad not found"));
        existingActividad.setNombre(actividad.getNombre());
        existingActividad.setDescripcion(actividad.getDescripcion());
        existingActividad.setFecha(actividad.getFecha());
        existingActividad.setLinkReunion(actividad.getLinkReunion());
        existingActividad.setArea(actividad.getArea());
        existingActividad.setEstado(actividad.getEstado());
        return actividadRepository.save(existingActividad);
    }

    @Override
    public void deleteActividad(Long id) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad not found"));
        actividadRepository.delete(actividad);
    }

    @Override
    public Page<Actividad> listAllActividades(Pageable pageable) {
        return actividadRepository.findAll(pageable);
    }

    @Override
    public Optional<Actividad> getActividadById(Long id) {
        return actividadRepository.findById(id);
    }

    @Override
    public Actividad cambiarEstado(Long id, Actividad.Estado estado) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad not found"));
        actividad.setEstado(estado);
        return actividadRepository.save(actividad);
    }

    @Override
    public Actividad asignarFecha(Long id, Date fecha) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad not found"));
        actividad.setFecha(fecha);
        return actividadRepository.save(actividad);
    }
}
