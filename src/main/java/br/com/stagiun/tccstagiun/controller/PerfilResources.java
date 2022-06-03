package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Perfil;
import br.com.stagiun.tccstagiun.model.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/perfis")
public class PerfilResources {

    @Autowired
    private PerfilService perfilService;

    @GetMapping
    public ResponseEntity<List<Perfil>> pegarTodos() {
        List<Perfil> list = perfilService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> pegarPorId(@PathVariable("id") Long id) {
        Optional<Perfil> perfil = perfilService.findById(id);

        if (perfil.isPresent()) {
            return ResponseEntity.ok(perfil.get());
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Perfil> salvar(@RequestBody Perfil perfil) throws ResourceFoundException {
        Perfil perfilRetornado = perfilService.salvar(perfil);

        if (perfilRetornado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(perfilRetornado.getId()).toUri();
            return ResponseEntity.created(uri).body(perfilRetornado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> editar(@PathVariable("id") Long id, @RequestBody Perfil perfil) throws ResourceFoundException {
        Perfil perfilEditado = perfilService.editar(id, perfil);

        if (perfilEditado != null) {
            return ResponseEntity.ok(perfilEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        perfilService.delete(id);
        return ResponseEntity.ok().build();
    }


}
