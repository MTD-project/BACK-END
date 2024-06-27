package com.makethedifference.mtd.services.impl;

import com.makethedifference.mtd.domain.entity.Actividad;
import com.makethedifference.mtd.domain.entity.Archivo;
import com.makethedifference.mtd.infra.repository.ArchivoRepository;
import com.makethedifference.mtd.services.ActividadService;
import com.makethedifference.mtd.services.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArchivoServiceImpl implements ArchivoService {

    private final Path rootLocation = Paths.get("archivo-storage");

    @Autowired
    private ArchivoRepository archivoRepository;

    @Autowired
    private ActividadService actividadService;

    @Override
    public Archivo almacenar(MultipartFile file, Long actividadId) throws IOException {
        Actividad actividad = actividadService.getActividadById(actividadId)
                .orElseThrow(() -> new RuntimeException("Actividad not found"));

        String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), this.rootLocation.resolve(nombreArchivo));

        Archivo archivo = new Archivo();
        archivo.setNombreArchivo(nombreArchivo);
        archivo.setTipoArchivo(file.getContentType());
        archivo.setRutaArchivo(this.rootLocation.resolve(nombreArchivo).toString());
        archivo.setActividad(actividad);

        return archivoRepository.save(archivo);
    }

    @Override
    public void eliminar(Long id) {
        archivoRepository.deleteById(id);
    }

    @Override
    public Optional<Archivo> getArchivoById(Long id) {
        return archivoRepository.findById(id);
    }

    @Override
    public List<Archivo> listarArchivosPorActividad(Long actividadId) {
        Actividad actividad = actividadService.getActividadById(actividadId)
                .orElseThrow(() -> new RuntimeException("Actividad not found"));
        return archivoRepository.findByActividad(actividad);
    }
}
