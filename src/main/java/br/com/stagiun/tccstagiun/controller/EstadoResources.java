package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.model.domain.Estado;
import br.com.stagiun.tccstagiun.model.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/estados")
public class EstadoResources {

    @Autowired
    private EstadoService estadoService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<Estado>> list() {
        List<Estado> list = estadoService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Estado> findById(@PathVariable("id") Long id){
        Optional<Estado> estado = estadoService.findById(id);

        if (estado.isPresent()) {
            return ResponseEntity.ok(estado.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Estado> salvar(@RequestBody Estado estado) {
        Estado estadoCadastrado = estadoService.salvar(estado);

        if (estadoCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(estadoCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(estadoCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Estado> editar(@PathVariable("id") Long id, @RequestBody Estado estado){
        Estado estadoEditado = estadoService.editar(id, estado);

        if(estadoEditado != null) {
            return ResponseEntity.ok(estadoEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        estadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
