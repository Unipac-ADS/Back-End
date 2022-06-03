package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Dica;
import br.com.stagiun.tccstagiun.model.service.DicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/dicas")
public class DicaResources {

    @Autowired
    private DicaService dicaService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<Dica>> list() {
        List<Dica> list = dicaService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Dica> findById(@PathVariable("id") Long id){
        Optional<Dica> dica = dicaService.findById(id);

        if (dica.isPresent()) {
            return ResponseEntity.ok(dica.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Dica> salvar(@RequestBody Dica dica) throws ResourceFoundException {
        Dica dicaCadastrado = dicaService.salvar(dica);

        if (dicaCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dicaCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(dicaCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Dica> editar(@PathVariable("id") Long id, @RequestBody Dica dica) throws ResourceFoundException {
        Dica dicaEditado = dicaService.editar(id, dica);

        if(dicaEditado != null) {
            return ResponseEntity.ok(dica);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        dicaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
