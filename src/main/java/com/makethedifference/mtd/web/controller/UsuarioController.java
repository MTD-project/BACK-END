package com.makethedifference.mtd.web.controller;

import java.util.List;
import java.util.Map;
import com.makethedifference.mtd.domain.dto.usuarioDto.DatosActualizarUsuario;
import com.makethedifference.mtd.domain.dto.usuarioDto.DatosListadoUsuario;
import com.makethedifference.mtd.domain.dto.usuarioDto.DatosRegistrarUsuario;
import com.makethedifference.mtd.domain.entity.Rol;
import com.makethedifference.mtd.domain.entity.Usuario;
import com.makethedifference.mtd.infra.security.LoginRequest;
import com.makethedifference.mtd.infra.security.TokenResponse;
import com.makethedifference.mtd.services.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuario")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Inicia sesión y genera un token para el usuario.
     *
     * @param request La solicitud de inicio de sesión.
     * @return La respuesta con el token generado.
     */
    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            TokenResponse tokenResponse = usuarioService.login(request);
            return ResponseEntity.ok(tokenResponse);
        } catch (DisabledException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cuenta deshabilitada.");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error en la autenticación.");
        }
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param datos Los datos del usuario a registrar.
     * @return La respuesta con el token generado.
     */
    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<TokenResponse> addUsuario(@RequestBody @Valid DatosRegistrarUsuario datos) {
        return ResponseEntity.ok(usuarioService.addUsuario(datos));
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    @GetMapping("/{id}")
    public Usuario findById(@PathVariable Long id){
        return usuarioService.getUsuarioById(id).orElse(null);
    }

    /**
     * Obtiene el perfil del usuario autenticado.
     *
     * @return Los datos del usuario autenticado.
     */
    @GetMapping("/perfil")
    public DatosListadoUsuario obtenerPerfil() {
        Usuario usuario = usuarioService.obtenerUsuarioAutenticado();
        return new DatosListadoUsuario(usuario);
    }

    /**
     * Elimina la cuenta del usuario autenticado.
     *
     * @return La respuesta indicando que la cuenta fue eliminada exitosamente.
     */
    @DeleteMapping("/perfil/eliminar")
    public ResponseEntity<String> eliminarCuenta() {
        // Obtener al usuario autenticado
        Usuario usuarioAutenticado = usuarioService.obtenerUsuarioAutenticado();

        // Deshabilitar  al usuario autenticado
        usuarioService.eliminarUsuario(usuarioAutenticado);

        return ResponseEntity.ok("Cuenta eliminada exitosamente");
    }


    /**
     * Actualiza el perfil del usuario autenticado.
     *
     * @param datos Los nuevos datos del usuario.
     * @return Los datos actualizados del usuario.
     */
    @PutMapping("/perfil/actualizar")
    @Transactional
    public ResponseEntity<DatosListadoUsuario> actualizarPerfil(@RequestBody @Valid DatosActualizarUsuario datos) {
        Usuario usuarioAutenticado = usuarioService.obtenerUsuarioAutenticado();

        usuarioAutenticado.actualizarUsuario(datos);
        usuarioService.updateUsuario(usuarioAutenticado);

        return ResponseEntity.ok(new DatosListadoUsuario(usuarioAutenticado));
    }


    /**
     * Lista todos los usuarios.
     *
     * @return La lista de usuarios.
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Lista todos los roles disponibles.
     *
     * @return La lista de roles.
     */
    @GetMapping("/roles")
    public ResponseEntity<List<String>> listarRoles() {
        List<String> roles = List.of(Rol.ADMIN.name(), Rol.MAKER.name(), Rol.LIDER.name(), Rol.DIRECTOR.name());
        return ResponseEntity.ok(roles);
    }

    /**
     * Actualiza los roles de varios usuarios.
     *
     * @return La respuesta indicando que los roles fueron actualizados exitosamente.
     */
    @PutMapping("/actualizar-roles")
    public ResponseEntity<Map<String, String>> actualizarRoles(@RequestBody Map<String, Object> request) {
        List<Integer> selectedUsers = (List<Integer>) request.get("selectedUsers");
        String newRole = (String) request.get("newRole");
        usuarioService.cambiarRolUsuarios(selectedUsers, newRole);

        return ResponseEntity.ok(Map.of("message", "Roles actualizados exitosamente"));
    }
}