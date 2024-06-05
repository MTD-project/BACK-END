package com.makethedifference.mtd.domain.entity;

// Enumeración que define los diferentes roles de usuario en el sistema.
public enum Rol {
    ADMIN,      // Rol de administrador, con permisos completos
    MAKER,      // Rol de creador o usuario estándar
    LIDER,      // Rol de líder, posiblemente con permisos elevados respecto a MAKER
    DIRECTOR    // Rol de director, con permisos específicos y posiblemente elevados
}
