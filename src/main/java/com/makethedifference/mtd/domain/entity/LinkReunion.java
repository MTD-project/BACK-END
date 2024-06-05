package com.makethedifference.mtd.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "link_reunion")
@Getter
@Setter
public class LinkReunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    private String url;

    private String titulo;

    private String asunto;

    // Constructor vacío
    public LinkReunion() {}

    // Constructor con parámetros
    public LinkReunion(Date fecha, String url, String titulo, String asunto) {
        this.fecha = fecha;
        this.url = url;
        this.titulo = titulo;
        this.asunto = asunto;
    }

    // Método para crear LinkReunion
    public static LinkReunion crearLink(Date fecha, String url, String titulo, String asunto) {
        return new LinkReunion(fecha, url, titulo, asunto);
    }
}