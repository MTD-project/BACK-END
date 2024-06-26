package com.makethedifference.mtd.domain.dto.usuarioDto;

import com.makethedifference.mtd.domain.entity.Usuario;

// Record para almacenar detalles del usuario
public record DatosDetallesUsuario(
        String nombre,   // El nombre del usuario
        String apellido, // El apellido del usuario
        String telefono, // El número de teléfono del usuario
        String correo    // El correo electrónico del usuario
) {
    // Constructor que inicializa el record con un objeto Usuario
    public DatosDetallesUsuario(Usuario usuario) {
        this(usuario.getNombre(), usuario.getApellido(), usuario.getTelefono(), usuario.getCorreo());
    }
}
