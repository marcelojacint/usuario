package com.marcelodev.usuario.business;

import com.marcelodev.usuario.business.converter.UsuarioConverter;
import com.marcelodev.usuario.business.dto.EnderecoDTO;
import com.marcelodev.usuario.business.dto.TelefoneDTO;
import com.marcelodev.usuario.business.dto.UsuarioDTO;
import com.marcelodev.usuario.infrastructure.entity.Endereco;
import com.marcelodev.usuario.infrastructure.entity.Telefone;
import com.marcelodev.usuario.infrastructure.entity.Usuario;
import com.marcelodev.usuario.infrastructure.exception.ConflicException;
import com.marcelodev.usuario.infrastructure.exception.ResourceNotFoundException;
import com.marcelodev.usuario.infrastructure.repository.EnderecoRepository;
import com.marcelodev.usuario.infrastructure.repository.TelefoneRepository;
import com.marcelodev.usuario.infrastructure.repository.UsuarioRepository;
import com.marcelodev.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioConverter converter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("usuário não encontrado!" + email));
        return converter.paraUsuarioDTO(usuario);
    }

    public void removerUsuario(String email) {

        repository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        Usuario usuarioEntity = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("email não encontrado!"));

        Usuario usuario = converter.updateUsuario(dto, usuarioEntity);

        return converter.paraUsuarioDTO(repository.save(usuario));

    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {

        Endereco enderecoEntity = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new ResourceNotFoundException("endereço não encontrado " + idEndereco));

        Endereco endereco = converter.updateEndereco(enderecoDTO, enderecoEntity);

        return converter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {
        Telefone telefoneEntity = telefoneRepository.findById(idTelefone)
                .orElseThrow(() -> new ResourceNotFoundException("telefone não encontrado! " + idTelefone));

        Telefone telefone = converter.updateTelefone(telefoneDTO, telefoneEntity);

        return converter.paraTelefoneDTO(telefoneRepository.save(telefone));

    }

}
