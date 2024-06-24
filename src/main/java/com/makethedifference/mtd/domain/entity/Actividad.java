package com.makethedifference.mtd.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "actividad")
@Getter
@Setter
public class Actividad {

    public enum Estado {
        PENDIENTE,
        EN_CURSO,
        FINALIZADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // Identificador único de la actividad

    private String nombre;  // Nombre de la actividad

    private String descripcion; // Descripción de la actividad

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha; // Fecha y hora de la actividad

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "linkreunion_id", referencedColumnName = "id")
    private LinkReunion linkReunion;    // Enlace a la reunión asociada

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;    // Relación con la entidad Area

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDIENTE; // Estado de la actividad

    // Constructor vacío
    public Actividad() {}

    // Constructor con parámetros
    public Actividad(String nombre, String descripcion, Date fecha, LinkReunion linkReunion, Area area) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.linkReunion = linkReunion;
        this.area = area;
        this.estado = Estado.PENDIENTE;
    }

    // Método estático para crear una nueva actividad
    public static Actividad crearActividad(String nombre, String descripcion, Date fecha, LinkReunion linkReunion, Area area) {
        return new Actividad(nombre, descripcion, fecha, linkReunion, area);
    }
}
