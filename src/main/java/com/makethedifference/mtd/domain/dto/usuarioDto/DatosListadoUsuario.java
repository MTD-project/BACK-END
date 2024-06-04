package com.makethedifference.mtd.domain.dto.usuarioDto;

import com.makethedifference.mtd.domain.entity.Usuario;

public record DatosListadoUsuario(
        String nombre,
        String telefono,
        String correo
) {
    public DatosListadoUsuario(Usuario usuario) {
        this(usuario.getNombre(),
                usuario.getTelefono(), usuario.getCorreo());
    }
}
