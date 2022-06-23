package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.controller.assemble.ResourcesAssemble;
import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Vaga;
import br.com.stagiun.tccstagiun.model.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourcesAssemble.V_1 + "vagas")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VagaResources extends ResourcesAssemble {

    @Autowired
    private VagaService vagaService;

    @GetMapping
    public ResponseEntity<List<Vaga>> pegarTodos() {
        List<Vaga> list = vagaService.list();

        if (list != null) {
           return ResponseEntity.ok(list);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaga> pegarPorId(@PathVariable("id") Long id) {
        Optional<Vaga> vagas = vagaService.findById(id);

        if (vagas.isPresent()) {
           return ResponseEntity.ok(vagas.get());
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Vaga> salvar(@RequestBody Vaga vaga) throws ResourceFoundException {
        Vaga vagaRetornado = vagaService.salvar(vaga);

        if (vagaRetornado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vagaRetornado.getId()).toUri();
            return ResponseEntity.created(uri).body(vagaRetornado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaga> editar(@PathVariable("id") Long id, @RequestBody Vaga vaga) throws ResourceFoundException {
        Vaga vagaRetornado = vagaService.editar(id, vaga);

        if (vagaRetornado != null) {
            return ResponseEntity.ok(vaga);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        vagaService.delete(id);
        return ResponseEntity.ok().build();
    }

}
