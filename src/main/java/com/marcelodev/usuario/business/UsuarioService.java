package com.marcelodev.usuario.business;

import com.marcelodev.usuario.business.converter.UsuarioConverter;
import com.marcelodev.usuario.business.dto.UsuarioDTO;
import com.marcelodev.usuario.infrastructure.entity.Usuario;
import com.marcelodev.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioConverter converter;

    public UsuarioDTO salvaUsuario(UsuarioDTO dto) {
        Usuario usuario = converter.paraUsuario(dto);
        Usuario usuarioSalvo = repository.save(usuario);

        return converter.paraUsuarioDTO(usuarioSalvo);

    }
}
