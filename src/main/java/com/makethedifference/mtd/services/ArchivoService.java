package com.makethedifference.mtd.services;

import com.makethedifference.mtd.domain.entity.Archivo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ArchivoService {
    Archivo almacenar(MultipartFile file, Long actividadId) throws IOException;
    void eliminar(Long id);
    Optional<Archivo> getArchivoById(Long id);
    List<Archivo> listarArchivosPorActividad(Long actividadId);
}
