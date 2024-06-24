package com.makethedifference.mtd.domain.dto.actividadDto;

import com.makethedifference.mtd.domain.entity.Actividad.Estado;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoRequest {
    private Estado estado;
}
