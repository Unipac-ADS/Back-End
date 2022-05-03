package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;
import br.com.stagiun.tccstagiun.model.service.AcompanhamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/api/v1/acompanhamentos")
public class AcompanhamentoResources {

    @Autowired
    private AcompanhamentoService acompanhamentoService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<Acompanhamento>> list() {
        List<Acompanhamento> list = acompanhamentoService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Acompanhamento> findById(@PathVariable("id") Long id){
        Optional<Acompanhamento> acompanhamento = acompanhamentoService.findById(id);

        if (acompanhamento.isPresent()) {
            return ResponseEntity.ok(acompanhamento.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Acompanhamento> salvar(@RequestBody Acompanhamento acompanhamento) throws ResourceFoundException {
        Acompanhamento acompanhamentoCadastrado = acompanhamentoService.salvar(acompanhamento);

        if (acompanhamentoCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(acompanhamentoCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(acompanhamentoCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Acompanhamento> editar(@PathVariable("id") Long id, @RequestBody Acompanhamento acompanhamento) throws ResourceFoundException {
        Acompanhamento acompanhamentoEditado = acompanhamentoService.editar(id, acompanhamento);

        if(acompanhamentoEditado != null) {
            return ResponseEntity.ok(acompanhamentoEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        acompanhamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
