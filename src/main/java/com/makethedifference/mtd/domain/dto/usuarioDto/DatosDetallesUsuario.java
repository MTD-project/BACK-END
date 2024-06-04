package com.makethedifference.mtd.domain.dto.usuarioDto;

import com.makethedifference.mtd.domain.entity.Usuario;

public record DatosDetallesUsuario(
        String nombre,
        String telefono,
        String correo
) {
    public DatosDetallesUsuario(Usuario usuario) {
        this(usuario.getNombre(), usuario.getTelefono(), usuario.getCorreo() );
    }
}
