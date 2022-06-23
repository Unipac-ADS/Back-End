package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.controller.assemble.ResourcesAssemble;
import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Curso;
import br.com.stagiun.tccstagiun.model.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourcesAssemble.V_1 + "cursos")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CursoResources extends ResourcesAssemble {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<Curso>> list() {
        List<Curso> list = cursoService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Curso> findById(@PathVariable("id") Long id){
        Optional<Curso> curso = cursoService.findById(id);

        if (curso.isPresent()) {
            return ResponseEntity.ok(curso.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Curso> salvar(@RequestBody Curso curso) throws ResourceFoundException {
        Curso cursoCadastrado = cursoService.salvar(curso);

        if (cursoCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cursoCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(cursoCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Curso> editar(@PathVariable("id") Long id, @RequestBody Curso curso) throws ResourceFoundException {
        Curso cursoEditado = cursoService.editar(id, curso);

        if(cursoEditado != null) {
            return ResponseEntity.ok(cursoEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
