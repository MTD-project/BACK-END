package com.makethedifference.mtd.services;

import com.makethedifference.mtd.domain.entity.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AreaService {
    Area createArea(Area area);
    Area updateArea(Long id, Area area);
    void deleteArea(Long id);
    Page<Area> listAllAreas(Pageable pageable);
    Optional<Area> getAreaById(Long id);
}
