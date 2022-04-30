package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.model.domain.UsuarioPerfil;
import br.com.stagiun.tccstagiun.model.repository.UsuarioPerfilRepository;
import br.com.stagiun.tccstagiun.model.service.UsuarioPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuariosperfis")
public class UsuarioPerfilResources {

    @Autowired
    private UsuarioPerfilService usuarioperfilService;

    @GetMapping
    public ResponseEntity<List<UsuarioPerfil>> pegarTodos() {
        List<UsuarioPerfil> usuarioperfil = usuarioperfilService.list();

        if (!usuarioperfil.isEmpty()) {
            return ResponseEntity.ok(usuarioperfil);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioPerfil> pegarPorId(@PathVariable("id") Long id) {
        Optional<UsuarioPerfil> usuarioperfil = usuarioperfilService.findById(id);

        if (usuarioperfil.isPresent()) {
            return ResponseEntity.ok(usuarioperfil.get());
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UsuarioPerfil> salvar(@RequestBody UsuarioPerfil usuarioperfil) {
        UsuarioPerfil usuarioperfilRetornado = usuarioperfilService.salvar(usuarioperfil);

        if (usuarioperfil != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuarioperfilRetornado.getId()).toUri();
            return ResponseEntity.created(uri).body(usuarioperfil);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioPerfil> salvar(@PathVariable("id") Long id, @RequestBody UsuarioPerfil usuarioperfil) {
        UsuarioPerfil usuarioperfilRetornado = usuarioperfilService.editar(id, usuarioperfil);

        if (usuarioperfilRetornado != null) {
            return ResponseEntity.ok(usuarioperfil);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        usuarioperfilService.delete(id);
        return ResponseEntity.ok().build();
    }


}
