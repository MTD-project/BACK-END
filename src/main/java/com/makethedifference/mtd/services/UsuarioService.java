package com.makethedifference.mtd.services;

import com.makethedifference.mtd.domain.dto.usuarioDto.DatosRegistrarUsuario;
import com.makethedifference.mtd.domain.entity.Rol;
import com.makethedifference.mtd.domain.entity.Usuario;
import com.makethedifference.mtd.infra.security.LoginRequest;
import com.makethedifference.mtd.infra.security.TokenResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface UsuarioService {

    TokenResponse login(LoginRequest request);

    TokenResponse addUsuario(DatosRegistrarUsuario usuario);

    Page<Usuario> getAllUsuarios(Pageable pageable);

    Optional<Usuario> getUsuarioById(Long id);

    Usuario obtenerUsuarioAutenticado();

    void cambiarRolUsuario(Long usuarioId, Rol nuevoRol);

    void updateUsuario(Usuario usuario);

    void eliminarUsuario(Usuario usuario);
}

