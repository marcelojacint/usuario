package com.marcelodev.usuario.business;

import com.marcelodev.usuario.business.converter.UsuarioConverter;
import com.marcelodev.usuario.business.dto.UsuarioDTO;
import com.marcelodev.usuario.infrastructure.entity.Usuario;
import com.marcelodev.usuario.infrastructure.exception.ConflicException;
import com.marcelodev.usuario.infrastructure.exception.ResourceNotFoundException;
import com.marcelodev.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioConverter converter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO dto) {
        emailExiste(dto.getEmail());
        dto.setSenha(passwordEncoder.encode(dto.getSenha()));
        Usuario usuario = converter.paraUsuario(dto);
        Usuario usuarioSalvo = repository.save(usuario);

        return converter.paraUsuarioDTO(usuarioSalvo);

    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflicException("email já cadastrado " + email);
            }
        } catch (ConflicException e) {
            throw new RuntimeException("email já cadastrado " + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return repository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("usuário não encontrado!" + email));
    }

    public void removerUsuario(String email) {
        repository.deleteByEmail(email);
    }

}
