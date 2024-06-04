package com.makethedifference.mtd.domain.dto.usuarioDto;

import com.makethedifference.mtd.domain.entity.Usuario;

public record DatosListadoUsuario(
        String nombre,
        String apellido,
        String telefono,
        String correo
) {
    public DatosListadoUsuario(Usuario usuario) {
        this(usuario.getNombre(), usuario.getApellido(),usuario.getTelefono(), usuario.getCorreo());
    }
}
