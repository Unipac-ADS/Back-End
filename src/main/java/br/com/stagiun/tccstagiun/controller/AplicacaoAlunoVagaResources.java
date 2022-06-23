package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.controller.assemble.ResourcesAssemble;
import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.AplicacaoAlunoVaga;
import br.com.stagiun.tccstagiun.model.service.AplicacaoAlunoVagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourcesAssemble.V_1 + "aplicacao-aluno-vagas")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AplicacaoAlunoVagaResources extends ResourcesAssemble {

    @Autowired
    private AplicacaoAlunoVagaService aplicacaoAlunoVagaService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<AplicacaoAlunoVaga>> list() {
        List<AplicacaoAlunoVaga> list = aplicacaoAlunoVagaService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<AplicacaoAlunoVaga> findById(@PathVariable("id") Long id){
        Optional<AplicacaoAlunoVaga> aplicacaoAlunoVaga = aplicacaoAlunoVagaService.findById(id);

        if (aplicacaoAlunoVaga.isPresent()) {
            return ResponseEntity.ok(aplicacaoAlunoVaga.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<AplicacaoAlunoVaga> salvar(@RequestBody AplicacaoAlunoVaga aplicacaoAlunoVaga) throws ResourceFoundException {
        AplicacaoAlunoVaga aplicacaoAlunoVagaCadastrado = aplicacaoAlunoVagaService.salvar(aplicacaoAlunoVaga);

        if (aplicacaoAlunoVagaCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(aplicacaoAlunoVagaCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(aplicacaoAlunoVagaCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<AplicacaoAlunoVaga> editar(@PathVariable("id") Long id, @RequestBody AplicacaoAlunoVaga aplicacaoAlunoVaga) throws ResourceFoundException {
        AplicacaoAlunoVaga aplicacaoAlunoVagaEditado = aplicacaoAlunoVagaService.editar(id, aplicacaoAlunoVaga);

        if(aplicacaoAlunoVagaEditado != null) {
            return ResponseEntity.ok(aplicacaoAlunoVagaEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        aplicacaoAlunoVagaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
