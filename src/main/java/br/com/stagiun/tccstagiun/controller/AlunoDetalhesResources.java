package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhes;
import br.com.stagiun.tccstagiun.model.service.AlunoDetalhesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/alunosDetalhes")
public class AlunoDetalhesResources {

    @Autowired
    private AlunoDetalhesService alunoDetalhesService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<AlunoDetalhes>> list() {
        List<AlunoDetalhes> list = alunoDetalhesService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<AlunoDetalhes> findById(@PathVariable("id") Long id){
        Optional<AlunoDetalhes> alunoDetalhes = alunoDetalhesService.findById(id);

        if (alunoDetalhes.isPresent()) {
            return ResponseEntity.ok(alunoDetalhes.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<AlunoDetalhes> salvar(@RequestBody AlunoDetalhes alunoDetalhes) {
        AlunoDetalhes alunoDetalhesCadastrado = alunoDetalhesService.salvar(alunoDetalhes);

        if (alunoDetalhesCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(alunoDetalhesCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(alunoDetalhesCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<AlunoDetalhes> editar(@PathVariable("id") Long id, @RequestBody AlunoDetalhes alunoDetalhes){
        AlunoDetalhes alunoDetalhesEditado = alunoDetalhesService.editar(id, alunoDetalhes);

        if(alunoDetalhesEditado != null) {
            return ResponseEntity.ok(alunoDetalhesEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        alunoDetalhesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
