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

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuario")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<TokenResponse> addUsuario(@RequestBody @Valid DatosRegistrarUsuario datos) {
        return ResponseEntity.ok(usuarioService.addUsuario(datos));
    }

    @GetMapping("/{id}")
    public Usuario findById(@PathVariable Long id){
        return usuarioService.getUsuarioById(id).orElse(null);
    }

    @GetMapping("/perfil")
    public DatosListadoUsuario obtenerPerfil() {
        Usuario usuario = usuarioService.obtenerUsuarioAutenticado();
        return new DatosListadoUsuario(usuario);
    }

    @DeleteMapping("/perfil/eliminar")
    public ResponseEntity<String> eliminarCuenta() {
        // Obtener al usuario autenticado
        Usuario usuarioAutenticado = usuarioService.obtenerUsuarioAutenticado();

        // Deshabilitar  al usuario autenticado
        usuarioService.eliminarUsuario(usuarioAutenticado);

        return ResponseEntity.ok("Cuenta eliminada exitosamente");
    }



    @PutMapping("/perfil/actualizar")
    @Transactional
    public ResponseEntity<DatosListadoUsuario> actualizarPerfil(@RequestBody @Valid DatosActualizarUsuario datos) {
        Usuario usuarioAutenticado = usuarioService.obtenerUsuarioAutenticado();

        usuarioAutenticado.actualizarUsuario(datos);
        usuarioService.updateUsuario(usuarioAutenticado);

        return ResponseEntity.ok(new DatosListadoUsuario(usuarioAutenticado));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<String>> listarRoles() {
        List<String> roles = List.of(Rol.ADMIN.name(), Rol.MAKER.name(), Rol.LIDER.name(), Rol.DIRECTOR.name());
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/actualizar-roles")
    public ResponseEntity<?> actualizarRoles(@RequestBody Map<String, Object> request) {
        List<Integer> selectedUsers = (List<Integer>) request.get("selectedUsers");
        String newRole = (String) request.get("newRole");
        usuarioService.cambiarRolUsuarios(selectedUsers, newRole);
        return ResponseEntity.ok("Roles actualizados exitosamente");
    }
}
