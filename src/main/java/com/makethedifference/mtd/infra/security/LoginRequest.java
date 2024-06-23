package com.makethedifference.mtd.infra.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una solicitud de inicio de sesión.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    String correo;  // Correo electrónico del usuario
    String password;    // Contraseña del usuario
}
