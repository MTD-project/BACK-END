package com.makethedifference.mtd.domain.dto.usuarioDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.Length;

// Record que almacena datos para registrar un usuario, con validaciones
public record DatosRegistrarUsuario(
        @NotBlank @Length(max = 100) String nombre,         // Nombre del usuario, no puede estar en blanco, máx. 100 caracteres
        @NotBlank @Length(max = 100) String apellido,       // Apellido del usuario, no puede estar en blanco, máx. 100 caracteres
        @NotNull @Size(min = 0, max = 9) String telefono,   // Teléfono del usuario, no nulo, máx. 9 caracteres
        @NotBlank @Email @Length(max = 100) String correo,  // Correo del usuario, no puede estar en blanco, debe ser un email válido, máx. 100 caracteres
        @NotBlank @Length(max = 100) String password        // Contraseña del usuario, no puede estar en blanco, máx. 100 caracteres
) {
}
