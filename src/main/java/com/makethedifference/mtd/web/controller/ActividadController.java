package com.makethedifference.mtd.web.controller;

import com.makethedifference.mtd.domain.dto.actividadDto.EstadoRequest;
import com.makethedifference.mtd.domain.entity.Actividad;
import com.makethedifference.mtd.domain.entity.Actividad.Estado;
import com.makethedifference.mtd.services.ActividadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/actividad")
@RequiredArgsConstructor
public class ActividadController {

    private final ActividadService actividadService;

    @PostMapping("/crear")
    public ResponseEntity<Actividad> createActividad(@RequestBody Actividad actividad) {
        return ResponseEntity.ok(actividadService.createActividad(actividad));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Actividad> updateActividad(@PathVariable Long id, @RequestBody Actividad actividad) {
        return ResponseEntity.ok(actividadService.updateActividad(id, actividad));
    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<Void> deleteActividad(@PathVariable Long id) {
        actividadService.deleteActividad(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Actividad>> listAllActividades(Pageable pageable) {
        return ResponseEntity.ok(actividadService.listAllActividades(pageable));
    }

    @GetMapping("/seleccionar/{id}")
    public ResponseEntity<Actividad> getActividadById(@PathVariable Long id) {
        return actividadService.getActividadById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/cambiarestado/{id}")
    public ResponseEntity<Actividad> cambiarEstado(@PathVariable Long id, @RequestBody EstadoRequest estadoRequest) {
        Estado estado = estadoRequest.getEstado();
        return ResponseEntity.ok(actividadService.cambiarEstado(id, estado));
    }

}
