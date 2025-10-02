package com.marcelodev.usuario.controller;

import com.marcelodev.usuario.business.UsuarioService;
import com.marcelodev.usuario.business.converter.UsuarioConverter;
import com.marcelodev.usuario.business.dto.UsuarioDTO;
import com.marcelodev.usuario.infrastructure.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioConverter converter;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(UsuarioDTO dto) {
        UsuarioDTO usuarioDTO = service.salvaUsuario(dto);

        Usuario usuario = converter.paraUsuario(usuarioDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("id")
                .buildAndExpand(usuario.getId())
                .toUri();

        return ResponseEntity.created(uri).body(usuarioDTO);
    }
}
