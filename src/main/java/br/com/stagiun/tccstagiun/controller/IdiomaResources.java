package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Idioma;
import br.com.stagiun.tccstagiun.model.service.IdiomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/idiomas")
public class IdiomaResources {

    @Autowired
    private IdiomaService idiomaService;

    @GetMapping
    public ResponseEntity<List<Idioma>> pegarTodos() {
        List<Idioma> idioma = idiomaService.list();

        if (!idioma.isEmpty()) {
           return ResponseEntity.ok(idioma);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idioma> pegarPorId(@PathVariable("id") Long id) {
        Optional<Idioma> list = idiomaService.findById(id);

        if (list.isPresent()) {
            return ResponseEntity.ok(list.get());
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Idioma> salvar(@RequestBody Idioma idioma) throws ResourceFoundException {
        Idioma idiomaRetornado = idiomaService.salvar(idioma);

        if (idiomaRetornado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(idiomaRetornado.getId()).toUri();
            return ResponseEntity.created(uri).body(idiomaRetornado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Idioma> editar(@PathVariable("id") Long id, @RequestBody Idioma idioma) throws ResourceFoundException {
        Idioma idiomaEditado = idiomaService.editar(id, idioma);

        if (idiomaEditado != null) {
            return ResponseEntity.ok(idiomaEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        idiomaService.delete(id);
        return ResponseEntity.ok().build();
    }


}
