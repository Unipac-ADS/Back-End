package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.controller.assemble.ResourcesAssemble;
import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.TipoEmpresa;
import br.com.stagiun.tccstagiun.model.service.TipoEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourcesAssemble.V_1 + "tipo-empresas")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TipoEmpresaResources extends ResourcesAssemble {

    @Autowired
    private TipoEmpresaService tipoEmpresaService;

    @GetMapping
    public ResponseEntity<List<TipoEmpresa>> pegarTodos() {
        List<TipoEmpresa> list = tipoEmpresaService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEmpresa> pegarPorId(@PathVariable("id") Long id) {
        Optional<TipoEmpresa> tipoEmpresa = tipoEmpresaService.findById(id);

        if (tipoEmpresa.isPresent()) {
            return ResponseEntity.ok(tipoEmpresa.get());
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<TipoEmpresa> salvar(@RequestBody TipoEmpresa tipoEmpresa) throws ResourceFoundException {
        TipoEmpresa tipoEmpresaRetornado = tipoEmpresaService.salvar(tipoEmpresa);

        if (tipoEmpresaRetornado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tipoEmpresaRetornado.getId()).toUri();
            return ResponseEntity.created(uri).body(tipoEmpresaRetornado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoEmpresa> editar(@PathVariable("id") Long id, @RequestBody TipoEmpresa tipoEmpresa) throws ResourceFoundException {
        TipoEmpresa tipoEmpresaEditado = tipoEmpresaService.editar(id, tipoEmpresa);

        if (tipoEmpresaEditado != null) {
            return ResponseEntity.ok(tipoEmpresaEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        tipoEmpresaService.delete(id);
        return ResponseEntity.ok().build();
    }


}
