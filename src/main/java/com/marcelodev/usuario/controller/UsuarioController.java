package com.marcelodev.usuario.controller;

import com.marcelodev.usuario.business.UsuarioService;
import com.marcelodev.usuario.business.converter.UsuarioConverter;
import com.marcelodev.usuario.business.dto.EnderecoDTO;
import com.marcelodev.usuario.business.dto.TelefoneDTO;
import com.marcelodev.usuario.business.dto.UsuarioDTO;
import com.marcelodev.usuario.infrastructure.entity.Usuario;
import com.marcelodev.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioConverter converter;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO dto) {
        UsuarioDTO usuarioDTO = service.salvaUsuario(dto);

        Usuario usuario = converter.paraUsuario(usuarioDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("id")
                .buildAndExpand(usuario.getId())
                .toUri();

        return ResponseEntity.created(uri).body(usuarioDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(),
                        dto.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@RequestParam("email") String email) {
        UsuarioDTO usuarioEncontrado = service.buscarUsuarioPorEmail(email);

        return ResponseEntity.ok(usuarioEncontrado);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> remover(@PathVariable String email) {
        service.removerUsuario(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> atualizaUsuario(@RequestBody UsuarioDTO dto, @RequestHeader("Authorization") String token) {
        service.atualizaDadosUsuario(token, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestParam("id") Long id, @RequestBody EnderecoDTO dto) {

        EnderecoDTO enderecoDTO = service.atualizaEndereco(id, dto);

        return ResponseEntity.ok(enderecoDTO);

    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestParam("id") Long id, @RequestBody TelefoneDTO dto) {

        TelefoneDTO telefoneDTO = service.atualizaTelefone(id, dto);

        return ResponseEntity.ok(telefoneDTO );

    }
}
