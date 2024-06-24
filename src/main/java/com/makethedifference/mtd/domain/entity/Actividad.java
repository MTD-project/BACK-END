package com.makethedifference.mtd.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "actividad")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private Area area;    // Relación con la entidad Area

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDIENTE; // Estado de la actividad
}
