package com.makethedifference.mtd.domain.dto.usuarioDto;

import com.makethedifference.mtd.domain.entity.Usuario;

// Record para almacenar datos básicos del usuario
public record DatosListadoUsuario(
        String nombre,
        String apellido,
        String telefono,
        String correo
) {
    // Constructor que inicializa el record con un objeto Usuario
    public DatosListadoUsuario(Usuario usuario) {
        this(usuario.getNombre(), usuario.getApellido(), usuario.getTelefono(), usuario.getCorreo());
    }
}
