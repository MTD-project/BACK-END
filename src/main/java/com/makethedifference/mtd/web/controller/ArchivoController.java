package com.makethedifference.mtd.web.controller;

import com.makethedifference.mtd.domain.entity.Archivo;
import com.makethedifference.mtd.services.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/archivos")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    @PostMapping("/subir")
    public ResponseEntity<Archivo> subirArchivo(@RequestParam("file") MultipartFile file, @RequestParam("actividadId") Long actividadId) {
        try {
            Archivo archivo = archivoService.almacenar(file, actividadId);
            return ResponseEntity.ok(archivo);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/listar/{actividadId}")
    public ResponseEntity<List<Archivo>> listarArchivos(@PathVariable Long actividadId) {
        List<Archivo> archivos = archivoService.listarArchivosPorActividad(actividadId);
        return ResponseEntity.ok(archivos);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarArchivo(@PathVariable Long id) {
        archivoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/seleccionar/{id}")
    public ResponseEntity<Archivo> seleccionarArchivo(@PathVariable Long id) {
        Optional<Archivo> archivo = archivoService.getArchivoById(id);
        return archivo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
