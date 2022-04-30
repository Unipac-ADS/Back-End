package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.model.domain.Cep;
import br.com.stagiun.tccstagiun.model.service.CepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ceps")
public class CepResources {

    @Autowired
    private CepService cepService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<Cep>> list() {
        List<Cep> list = cepService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Cep> findById(@PathVariable("id") Long id){
        Optional<Cep> cep = cepService.findById(id);

        if (cep.isPresent()) {
            return ResponseEntity.ok(cep.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Cep> salvar(@RequestBody Cep cep) {
        Cep cepCadastrado = cepService.salvar(cep);

        if (cepCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cepCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(cepCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Cep> editar(@PathVariable("id") Long id, @RequestBody Cep cidade){
        Cep cidadeEditado = cepService.editar(id, cidade);

        if(cidadeEditado != null) {
            return ResponseEntity.ok(cidadeEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        cepService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
