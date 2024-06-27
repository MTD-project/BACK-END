package com.makethedifference.mtd.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "actividad")
@Getter
@Setter
@AllArgsConstructor
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // Identificador único de la actividad

    private String nombre;  // Nombre de la actividad

    @NotNull
    private String descripcion; // Descripción de la actividad

    public enum Estado {
        PENDIENTE,
        EN_CURSO,
        FINALIZADO
    }

    private LocalDate fecha; // Fecha de la actividad

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "linkreunion_id", referencedColumnName = "id")
    private LinkReunion linkReunion;    // Enlace a la reunión asociada

    @ManyToOne
    @JoinColumn(name = "area_id")
    @JsonBackReference
    private Area area; // Relación con la entidad Area

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDIENTE; // Estado de la actividad

    // Constructor vacío
    public Actividad() {
        this.fecha = LocalDate.now(); // Asignar fecha actual al crear una nueva actividad
    }
}
