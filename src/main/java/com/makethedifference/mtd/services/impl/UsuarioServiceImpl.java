package com.makethedifference.mtd.services.impl;

import com.makethedifference.mtd.domain.dto.usuarioDto.DatosRegistrarUsuario;
import com.makethedifference.mtd.domain.entity.Rol;
import com.makethedifference.mtd.domain.entity.Usuario;
import com.makethedifference.mtd.infra.exception.ValidacionDeIntegridad;
import com.makethedifference.mtd.infra.repository.UsuarioRepository;
import com.makethedifference.mtd.infra.security.JwtService;
import com.makethedifference.mtd.infra.security.LoginRequest;
import com.makethedifference.mtd.infra.security.TokenResponse;
import com.makethedifference.mtd.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenResponse login(LoginRequest request) {
        // Autentica al usuario con las credenciales proporcionadas
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Usuario user = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + request.getCorreo()));

        // Verifica si el usuario está habilitado
        if (!user.isEnabled()) {
            throw new DisabledException("Este usuario ha sido deshabilitado.");
        }

        // Genera un token JWT para el usuario autenticado
        String token = jwtService.getToken(user, user);
        return TokenResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public TokenResponse addUsuario(DatosRegistrarUsuario datos) {
        // Crea un nuevo usuario a partir de los datos de registro
        Usuario usuario = new Usuario();
        usuario.setNombre(datos.nombre());
        usuario.setApellido(datos.apellido());
        usuario.setTelefono(datos.telefono());
        usuario.setCorreo(datos.correo());
        usuario.setPassword(passwordEncoder.encode(datos.password()));
        usuario.setRol(Rol.MAKER);

        // Guarda el nuevo usuario en el repositorio
        usuarioRepository.save(usuario);

        // Genera un token JWT para el nuevo usuario
        String token = jwtService.getToken(usuario, usuario);
        return TokenResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public Page<Usuario> getAllUsuarios(Pageable pageable) {
        // Obtiene todos los usuarios de manera paginada
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Optional<Usuario> getUsuarioById(Long id) {
        // Busca un usuario por su ID
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario obtenerUsuarioAutenticado() {
        // Obtiene el usuario actualmente autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No hay usuario autenticado");
        }

        String currentUserName = authentication.getName();
        return usuarioRepository.findByCorreo(currentUserName)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    @Transactional
    @Override
    public void cambiarRolUsuario(Long usuarioId, Rol nuevoRol) {
        // Cambia el rol de un usuario específico
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setRol(nuevoRol);
        usuarioRepository.save(usuario);
    }

    @Transactional
    @Override
    public void updateUsuario(Usuario usuario) {
        // Valida y actualiza los datos de un usuario
        validarDatosUsuario(usuario);
        usuarioRepository.save(usuario);
    }

    private void validarDatosUsuario(Usuario usuario) {
        // Valida que el correo del usuario no sea nulo ni vacío
        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            throw new ValidacionDeIntegridad("El username no puede ser vacío");
        }
    }

    @Transactional
    @Override
    public void eliminarUsuario(Usuario usuario) {
        // Deshabilita un usuario en lugar de eliminarlo físicamente
        usuario.setEnabled(false);
        usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> findAll() {
        // Obtiene una lista de todos los usuarios
        return usuarioRepository.findAll();
    }

    @Override
    public List<String> getAllRoles() {
        // Devuelve una lista con todos los roles disponibles
        return List.of(Rol.ADMIN.name(), Rol.MAKER.name(), Rol.LIDER.name(), Rol.DIRECTOR.name());
    }

    @Transactional
    @Override
    public void cambiarRolUsuarios(List<Integer> usuarioIds, String nuevoRol) {
        // Cambia el rol de varios usuarios
        Rol rol = Rol.valueOf(nuevoRol);
        usuarioIds.forEach(usuarioId -> {
            Usuario usuario = usuarioRepository.findById(usuarioId.longValue())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            usuario.setRol(rol);
            usuarioRepository.save(usuario);
        });
    }
}

