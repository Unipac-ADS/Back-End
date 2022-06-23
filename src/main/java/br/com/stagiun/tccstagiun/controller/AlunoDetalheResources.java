package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.controller.assemble.ResourcesAssemble;
import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhe;
import br.com.stagiun.tccstagiun.model.service.AlunoDetalheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourcesAssemble.V_1 + "aluno-detalhes")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AlunoDetalheResources extends ResourcesAssemble {

    @Autowired
    private AlunoDetalheService alunoDetalheService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<AlunoDetalhe>> list() {
        List<AlunoDetalhe> list = alunoDetalheService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<AlunoDetalhe> findById(@PathVariable("id") Long id){
        Optional<AlunoDetalhe> alunoDetalhes = alunoDetalheService.findById(id);

        if (alunoDetalhes.isPresent()) {
            return ResponseEntity.ok(alunoDetalhes.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<AlunoDetalhe> salvar(@RequestBody AlunoDetalhe alunoDetalhes) throws ResourceFoundException {
        AlunoDetalhe alunoDetalhesCadastrado = alunoDetalheService.salvar(alunoDetalhes);

        if (alunoDetalhesCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(alunoDetalhesCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(alunoDetalhesCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<AlunoDetalhe> editar(@PathVariable("id") Long id, @RequestBody AlunoDetalhe alunoDetalhes) throws ResourceFoundException {
        AlunoDetalhe alunoDetalhesEditado = alunoDetalheService.editar(id, alunoDetalhes);

        if(alunoDetalhesEditado != null) {
            return ResponseEntity.ok(alunoDetalhesEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        alunoDetalheService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
