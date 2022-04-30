package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.model.domain.Pais;
import br.com.stagiun.tccstagiun.model.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/paises")
public class PaisResources {

    @Autowired
    private PaisService paisService;

    @GetMapping
    public ResponseEntity<List<Pais>> pegarTodos() {
        List<Pais> list = paisService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pais> pegarPorId(@PathVariable("id") Long id) {
        Optional<Pais> pais = paisService.findById(id);

        if (pais.isPresent()) {
            return ResponseEntity.ok(pais.get());
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Pais> salvar(@RequestBody Pais pais) {
        Pais paisRetornado = paisService.salvar(pais);

        if (paisRetornado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(paisRetornado.getId()).toUri();
            return ResponseEntity.created(uri).body(paisRetornado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pais> editar(@PathVariable("id") Long id, @RequestBody Pais pais) {
        Pais paisEditado = paisService.editar(id, pais);

        if (paisEditado != null) {
            return ResponseEntity.ok(paisEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        paisService.delete(id);
        return ResponseEntity.ok().build();
    }


}
