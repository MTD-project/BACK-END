package com.makethedifference.mtd.domain.dto.usuarioDto; // Define el paquete en el que se encuentra este record

// Record para actualizar los datos de un usuario
public record DatosActualizarUsuario(

        // Campos del record, cada uno representa una propiedad del usuario
        String nombre,   // El nombre del usuario
        String apellido, // El apellido del usuario
        String telefono, // El número de teléfono del usuario
        String correo    // El correo electrónico del usuario

) {
    // El cuerpo del record puede contener métodos adicionales, pero en este caso está vacío.
}
