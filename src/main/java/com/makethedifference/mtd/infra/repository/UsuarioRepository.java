package com.makethedifference.mtd.infra.repository;

import com.makethedifference.mtd.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio de JPA para la entidad Usuario.
 * Proporciona métodos para realizar operaciones CRUD en la entidad Usuario.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param correo El correo electrónico del usuario.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<Usuario> findByCorreo(String correo);
}
