package com.makethedifference.mtd.services;

import com.makethedifference.mtd.domain.dto.usuarioDto.DatosRegistrarUsuario;
import com.makethedifference.mtd.domain.entity.Rol;
import com.makethedifference.mtd.domain.entity.Usuario;
import com.makethedifference.mtd.infra.security.LoginRequest;
import com.makethedifference.mtd.infra.security.TokenResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para el servicio de gestión de usuarios.
 */
public interface UsuarioService {

    /**
     * Inicia sesión y genera un token para el usuario.
     *
     * @param request La solicitud de inicio de sesión.
     * @return La respuesta con el token generado.
     */
    TokenResponse login(LoginRequest request);

    /**
     * Registra un nuevo usuario.
     *
     * @param usuario Los datos del usuario a registrar.
     * @return La respuesta con el token generado.
     */
    TokenResponse addUsuario(DatosRegistrarUsuario usuario);

    /**
     * Obtiene una página de usuarios.
     *
     * @param pageable La información de paginación.
     * @return Una página de usuarios.
     */
    Page<Usuario> getAllUsuarios(Pageable pageable);

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<Usuario> getUsuarioById(Long id);

    /**
     * Obtiene el usuario actualmente autenticado.
     *
     * @return El usuario autenticado.
     */
    Usuario obtenerUsuarioAutenticado();

    /**
     * Cambia el rol de un usuario.
     *
     * @param usuarioId El ID del usuario.
     * @param nuevoRol  El nuevo rol para el usuario.
     */

    /**
     * Actualiza los datos de un usuario.
     *
     * @param usuario El usuario con los datos actualizados.
     */
    void updateUsuario(Usuario usuario);

    /**
     * Elimina un usuario.
     *
     * @param usuario El usuario a eliminar.
     */
    void eliminarUsuario(Usuario usuario);

    /**
     * Obtiene una lista de todos los usuarios.
     *
     * @return Una lista de todos los usuarios.
     */
    List<Usuario> findAll();

    /**
     * Obtiene una lista de todos los roles disponibles.
     *
     * @return Una lista de todos los roles.
     */
    List<String> getAllRoles();

    /**
     * Cambia el rol de varios usuarios.
     *
     * @param usuarioIds Los IDs de los usuarios.
     * @param nuevoRol El nuevo rol para los usuarios.
     */
    void cambiarRolUsuarios(List<Integer> usuarioIds, String nuevoRol);
}

