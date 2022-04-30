package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.model.domain.Usuario;
import br.com.stagiun.tccstagiun.model.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioResources {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> pegarTodos() {
        List<Usuario> list = usuarioService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> pegarPorId(@PathVariable("id") Long id) {
        Optional<Usuario> usuarios = usuarioService.findById(id);

        if (usuarios.isPresent()) {
            return ResponseEntity.ok(usuarios.get());
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        Usuario usuarioRetornado = usuarioService.salvar(usuario);

        if (usuarioRetornado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuarioRetornado.getId()).toUri();
            return ResponseEntity.created(uri).body(usuarioRetornado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editar(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        Usuario usuarioEditado = usuarioService.editar(id, usuario);

        if (usuarioEditado != null) {
            return ResponseEntity.ok(usuarioEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }


}
