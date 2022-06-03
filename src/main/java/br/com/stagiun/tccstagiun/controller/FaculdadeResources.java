package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Faculdade;
import br.com.stagiun.tccstagiun.model.service.FaculdadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/faculdades")
public class FaculdadeResources {

    @Autowired
    private FaculdadeService faculdadeService;

    @GetMapping
    public ResponseEntity<List<Faculdade>> pegarTodos() {
        List<Faculdade> list = faculdadeService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculdade> pegarPorId(@PathVariable("id") Long id) {
        Optional<Faculdade> faculdade = faculdadeService.findById(id);

        if (faculdade.isPresent()) {
            return ResponseEntity.ok(faculdade.get());
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Faculdade> salvar(@RequestBody Faculdade faculdade) throws ResourceFoundException {
        Faculdade faculdadeRetornado = faculdadeService.salvar(faculdade);

        if (faculdadeRetornado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(faculdadeRetornado.getId()).toUri();
            return ResponseEntity.created(uri).body(faculdadeRetornado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculdade> editar(@PathVariable("id") Long id, @RequestBody Faculdade faculdade) throws ResourceFoundException {
        Faculdade faculdadeEditado = faculdadeService.editar(id, faculdade);

        if (faculdadeEditado != null) {
            return ResponseEntity.ok(faculdadeEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        faculdadeService.delete(id);
        return ResponseEntity.ok().build();
    }


}
