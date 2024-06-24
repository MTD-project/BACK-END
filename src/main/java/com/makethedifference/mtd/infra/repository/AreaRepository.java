package com.makethedifference.mtd.infra.repository;

import com.makethedifference.mtd.domain.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {
}
