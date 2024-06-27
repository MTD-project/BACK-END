package com.makethedifference.mtd.domain.dto.usuarioDto; // Define el paquete en el que se encuentra este record

// Record para actualizar los datos de un usuario
public class DatosActualizarUsuario {
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}