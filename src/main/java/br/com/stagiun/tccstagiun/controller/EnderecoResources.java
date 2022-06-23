package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.controller.assemble.ResourcesAssemble;
import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Endereco;
import br.com.stagiun.tccstagiun.model.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourcesAssemble.V_1 + "enderecos")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EnderecoResources extends ResourcesAssemble {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<Endereco>> list() {
        List<Endereco> list = enderecoService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Endereco> findById(@PathVariable("id") Long id){
        Optional<Endereco> endereco = enderecoService.findById(id);

        if (endereco.isPresent()) {
            return ResponseEntity.ok(endereco.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Endereco> salvar(@RequestBody Endereco endereco) throws ResourceFoundException {
        Endereco enderecoCadastrado = enderecoService.salvar(endereco);

        if (enderecoCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(enderecoCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(enderecoCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Endereco> editar(@PathVariable("id") Long id, @RequestBody Endereco endereco) throws ResourceFoundException {
        Endereco enderecoEditado = enderecoService.editar(id, endereco);

        if(enderecoEditado != null) {
            return ResponseEntity.ok(enderecoEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
