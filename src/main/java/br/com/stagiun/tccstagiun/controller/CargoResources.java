package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.controller.assemble.ResourcesAssemble;
import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Cargo;
import br.com.stagiun.tccstagiun.model.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourcesAssemble.V_1 + "cargos")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CargoResources extends ResourcesAssemble {

    @Autowired
    private CargoService cargoService;

    @GetMapping
    @ResponseBody
    public  ResponseEntity<List<Cargo>> list() {
        List<Cargo> list = cargoService.list();

        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Cargo> findById(@PathVariable("id") Long id){
        Optional<Cargo> cargo = cargoService.findById(id);

        if (cargo.isPresent()) {
            return ResponseEntity.ok(cargo.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Cargo> salvar(@RequestBody Cargo cargo) throws ResourceFoundException {
        Cargo cargoCadastrado = cargoService.salvar(cargo);

        if (cargoCadastrado != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cargoCadastrado.getId()).toUri();
            return ResponseEntity.created(uri).body(cargoCadastrado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public  ResponseEntity<Cargo> editar(@PathVariable("id") Long id, @RequestBody Cargo cargo) throws ResourceFoundException {
        Cargo cargoEditado = cargoService.editar(id, cargo);

        if(cargoEditado != null) {
            return ResponseEntity.ok(cargoEditado);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        cargoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
