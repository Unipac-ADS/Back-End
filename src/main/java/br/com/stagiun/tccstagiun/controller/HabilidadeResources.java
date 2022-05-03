package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Habilidade;
import br.com.stagiun.tccstagiun.model.service.HabilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/habilidades")
public class HabilidadeResources {

    @Autowired
    private HabilidadeService habilidadeService;

    @GetMapping
    public ResponseEntity<List<Habilidade>> pegarTodos() {
        List<Habilidade> list = habilidadeService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habilidade> pegarPorId(@PathVariable("id") Long id) {
        Optional<Habilidade> habilidade = habilidadeService.findById(id);

        if (habilidade.isPresent()) {
            return ResponseEntity.ok(habilidade.get());
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Habilidade> salvar(@RequestBody Habilidade habilidade) throws ResourceFoundException {
        Habilidade habilidadeRetornado = habilidadeService.salvar(habilidade);

        if (habilidadeRetornado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(habilidadeRetornado.getId()).toUri();
            return ResponseEntity.created(uri).body(habilidadeRetornado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habilidade> editar(@PathVariable("id") Long id, @RequestBody Habilidade habilidade) throws ResourceFoundException {
        Habilidade habilidadeEditado = habilidadeService.editar(id, habilidade);

        if (habilidadeEditado != null) {
            return ResponseEntity.ok(habilidadeEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        habilidadeService.delete(id);
        return ResponseEntity.ok().build();
    }


}
