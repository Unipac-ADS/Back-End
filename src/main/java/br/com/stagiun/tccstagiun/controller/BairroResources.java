package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.controller.assemble.ResourcesAssemble;
import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Bairro;
import br.com.stagiun.tccstagiun.model.service.BairroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourcesAssemble.V_1 + "bairros")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BairroResources extends ResourcesAssemble {

    @Autowired
    private BairroService bairroService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<Bairro>> list() {
        List<Bairro> list = bairroService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Bairro> findById(@PathVariable("id") Long id){
        Optional<Bairro> bairro = bairroService.findById(id);

        if (bairro.isPresent()) {
            return ResponseEntity.ok(bairro.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Bairro> salvar(@RequestBody Bairro bairro) throws ResourceFoundException {
        Bairro bairroCadastrado = bairroService.salvar(bairro);

        if (bairroCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bairroCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(bairroCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Bairro> editar(@PathVariable("id") Long id, @RequestBody Bairro bairro) throws ResourceFoundException {
        Bairro bairroEditado = bairroService.editar(id, bairro);

        if(bairroEditado != null) {
            return ResponseEntity.ok(bairroEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        bairroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
