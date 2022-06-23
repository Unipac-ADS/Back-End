package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.controller.assemble.ResourcesAssemble;
import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Empresa;
import br.com.stagiun.tccstagiun.model.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourcesAssemble.V_1 + "empresas")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmpresaResources extends ResourcesAssemble {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<Empresa>> list() {
        List<Empresa> list = empresaService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Empresa> findById(@PathVariable("id") Long id){
        Optional<Empresa> empresa = empresaService.findById(id);

        if (empresa.isPresent()) {
            return ResponseEntity.ok(empresa.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Empresa> salvar(@RequestBody Empresa empresa) throws ResourceFoundException {
        Empresa empresaCadastrado = empresaService.salvar(empresa);

        if (empresaCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(empresaCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(empresaCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Empresa> editar(@PathVariable("id") Long id, @RequestBody Empresa empresa) throws ResourceFoundException {
        Empresa empresaEditado = empresaService.editar(id, empresa);

        if(empresaEditado != null) {
            return ResponseEntity.ok(empresaEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        empresaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
