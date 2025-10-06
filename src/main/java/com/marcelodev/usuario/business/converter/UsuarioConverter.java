package com.marcelodev.usuario.business.converter;

import com.marcelodev.usuario.business.dto.EnderecoDTO;
import com.marcelodev.usuario.business.dto.TelefoneDTO;
import com.marcelodev.usuario.business.dto.UsuarioDTO;
import com.marcelodev.usuario.infrastructure.entity.Endereco;
import com.marcelodev.usuario.infrastructure.entity.Telefone;
import com.marcelodev.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO dto) {
        return Usuario
                .builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .enderecos(this.paraListaEndereco(dto.getEnderecos()))
                .telefones(this.paraListaTelefone(dto.getTelefones()))
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO
                .builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(this.paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(this.paraListaTelefoneDTO(usuario.getTelefones()))
                .build();
    }

    private List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOs) {
        return enderecoDTOs.stream().map(this::paraEndereco).toList();

    }

    private Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco
                .builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    private List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOs) {
        return telefoneDTOs.stream().map(this::paraTelefone).toList();
    }

    private Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone
                .builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    private List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
        return enderecos.stream().map(this::paraEnderecoDTO).toList();

    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTO
                .builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    private List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefones) {
        return telefones.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTO
                .builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO dto, Usuario entity) {
        return Usuario
                .builder()
                .id(entity.getId())
                .nome(dto.getNome() != null ? dto.getNome() : entity.getNome())
                .email(dto.getEmail() != null ? dto.getEmail() : entity.getEmail())
                .senha(dto.getSenha() != null ? dto.getSenha() : entity.getSenha())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity) {
        return Endereco
                .builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity) {
       return Telefone
                .builder()
               .id(entity.getId())
               .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
               .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .build();
    }
}
