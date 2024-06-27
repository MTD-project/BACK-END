package com.makethedifference.mtd.services.impl;

import com.makethedifference.mtd.domain.entity.Area;
import com.makethedifference.mtd.infra.repository.AreaRepository;
import com.makethedifference.mtd.services.AreaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    @Override
    public Area createArea(Area area) {
        if (area.getActividades() != null) {
            area.getActividades().forEach(actividad -> actividad.setArea(area));
        }
        return areaRepository.save(area);
    }

    @Override
    public Area updateArea(Long id, Area area) {
        Area existingArea = areaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Area not found"));
        existingArea.setNombre(area.getNombre());
        existingArea.setDescripcion(area.getDescripcion());
        if (area.getActividades() != null) {
            area.getActividades().forEach(actividad -> actividad.setArea(existingArea));
        }
        existingArea.setActividades(area.getActividades());
        return areaRepository.save(existingArea);
    }

    @Override
    public void deleteArea(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Area not found"));
        areaRepository.delete(area);
    }

    @Override
    public Page<Area> listAllAreas(Pageable pageable) {
        return areaRepository.findAll(pageable);
    }

    @Override
    public Optional<Area> getAreaById(Long id) {
        return areaRepository.findById(id);
    }

    @Override
    public void limpiarAreas() {
        areaRepository.deleteAll();
    }
}
