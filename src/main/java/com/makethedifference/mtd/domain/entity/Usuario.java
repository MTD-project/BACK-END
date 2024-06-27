package com.makethedifference.mtd.domain.entity;

import com.makethedifference.mtd.domain.dto.usuarioDto.DatosActualizarUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "Usuario")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // Identificador único del usuario
    private String nombre;  // Nombre del usuario
    private String apellido;    // Apellido del usuario

    @Column(unique = true)
    private String telefono;    // Teléfono único del usuario

    @Column(unique = true)
    private String correo;  // Correo único del usuario

    @Column(name = "contraseña")
    private String password;    // Contraseña del usuario

    @Enumerated(EnumType.STRING)
    private Rol rol;    // Rol del usuario (ADMIN, MAKER, LIDER, DIRECTOR)

    @Column(updatable  = false)
    @CreationTimestamp
    private LocalDateTime fechaCreacion;    // Fecha de creación del registro

    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;   // Fecha de última actualización del registro

    @Column(nullable = false, name = "estado")
    private boolean enabled = true; // Indica si el usuario está habilitado

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "usuario")
    private Set<Actividad> actividad;

    // Método para actualizar datos del usuario
    public void actualizarUsuario(DatosActualizarUsuario datosActualizarUsuario){
        if (datosActualizarUsuario.nombre() != null){
            this.nombre = datosActualizarUsuario.nombre();
        }
        if (datosActualizarUsuario.nombre() != null){
            this.nombre = datosActualizarUsuario.apellido();
        }
        if (datosActualizarUsuario.telefono() != null){
            this.telefono = datosActualizarUsuario.telefono();
        }
        if (datosActualizarUsuario.correo() != null){
            this.correo = datosActualizarUsuario.correo();
        }
    }

    // Retorna la autoridad del usuario basada en su rol
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna la autoridad del usuario basada en su rol
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public String getUsername() {
        return this.correo;
    }   // Retorna el correo como nombre de usuario

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }   // Indica si la cuenta no ha expirado

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }   // Indica si la cuenta no está bloqueada

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }   // Indica si las credenciales no han expirado

    @Override
    public boolean isEnabled() {
        // Indica si la cuenta está habilitada
        return this.enabled;
    }

}
