package com.makethedifference.mtd.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;
    private String tipoArchivo;
    private String rutaArchivo;

    @ManyToOne
    @NotNull
    private Actividad actividad;

    // Constructor, getters y setters
}
