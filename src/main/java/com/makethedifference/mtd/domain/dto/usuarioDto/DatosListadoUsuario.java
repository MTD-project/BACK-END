package com.makethedifference.mtd.domain.dto.usuarioDto;

import com.makethedifference.mtd.domain.entity.Usuario;

// Record que almacena datos básicos del usuario
public record DatosListadoUsuario(
        String nombre,
        String apellido,
        String telefono,
        String correo
) {
    public DatosListadoUsuario(Usuario usuario) {
        // Constructor que inicializa el record con datos de un objeto Usuario
        this(usuario.getNombre(), usuario.getApellido(),usuario.getTelefono(), usuario.getCorreo());
    }
}
