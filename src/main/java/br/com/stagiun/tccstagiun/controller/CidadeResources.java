package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Cidade;
import br.com.stagiun.tccstagiun.model.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/cidades")
public class CidadeResources {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<Cidade>> list() {
        List<Cidade> list = cidadeService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Cidade> findById(@PathVariable("id") Long id){
        Optional<Cidade> cidade = cidadeService.findById(id);

        if (cidade.isPresent()) {
            return ResponseEntity.ok(cidade.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Cidade> salvar(@RequestBody Cidade cidade) throws ResourceFoundException {
        Cidade cidadeCadastrado = cidadeService.salvar(cidade);

        if (cidadeCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cidadeCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(cidadeCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Cidade> editar(@PathVariable("id") Long id, @RequestBody Cidade cidade) throws ResourceFoundException {
        Cidade cidadeEditado = cidadeService.editar(id, cidade);

        if(cidadeEditado != null) {
            return ResponseEntity.ok(cidadeEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        cidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
