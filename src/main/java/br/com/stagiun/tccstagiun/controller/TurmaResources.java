package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Turma;
import br.com.stagiun.tccstagiun.model.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/turmas")
public class TurmaResources {

    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public ResponseEntity<List<Turma>> pegarTodos() {
        List<Turma> list = turmaService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> pegarPorId(@PathVariable("id") Long id) {
        Optional<Turma> turma = turmaService.findById(id);

        if (turma.isPresent()) {
            return ResponseEntity.ok(turma.get());
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Turma> salvar(@RequestBody Turma turma) throws ResourceFoundException {
        Turma turmaRetornado = turmaService.salvar(turma);

        if (turmaRetornado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(turmaRetornado.getId()).toUri();
            return ResponseEntity.created(uri).body(turmaRetornado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> editar(@PathVariable("id") Long id, @RequestBody Turma turma) throws ResourceFoundException {
        Turma turmaEditado = turmaService.editar(id, turma);

        if (turmaEditado != null) {
            return ResponseEntity.ok(turmaEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        turmaService.delete(id);
        return ResponseEntity.ok().build();
    }


}
