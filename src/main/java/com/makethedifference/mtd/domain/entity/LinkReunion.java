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
    private Long id;    // Identificador único de la reunión

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha; // Fecha y hora de la reunión

    private String url; // URL del enlace de la reunión

    private String titulo;  // Título de la reunión

    private String asunto;  // Asunto de la reunión

    // Constructor vacío
    public LinkReunion() {}

    // Constructor con parámetros para inicializar todos los atributos
    public LinkReunion(Date fecha, String url, String titulo, String asunto) {
        this.fecha = fecha;
        this.url = url;
        this.titulo = titulo;
        this.asunto = asunto;
    }

    // Método estático para crear una nueva instancia de LinkReunion
    public static LinkReunion crearLink(Date fecha, String url, String titulo, String asunto) {
        return new LinkReunion(fecha, url, titulo, asunto);
    }
}