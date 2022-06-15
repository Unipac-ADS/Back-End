package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.exceptions.ResourceNotFoundException;
import br.com.stagiun.tccstagiun.filter.RegisterRequest;
import br.com.stagiun.tccstagiun.filter.RegisterResponse;
import br.com.stagiun.tccstagiun.model.domain.Perfil;
import br.com.stagiun.tccstagiun.model.domain.Usuario;
import br.com.stagiun.tccstagiun.model.service.PerfilService;
import br.com.stagiun.tccstagiun.model.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/v1/register")
public class RegisterController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PerfilService perfilService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest, UriComponentsBuilder uriBuilder) throws ResourceFoundException, ResourceNotFoundException {
        Optional<Perfil> perfilOptional = perfilService.findById(1L);
        Set<Perfil> perfils = new HashSet<>();
        perfils.add(perfilOptional.get());

        Usuario usuario = Usuario.builder().id(1L).nome(registerRequest.getNome()).email(registerRequest.getEmail()).senha(registerRequest.getSenha()).perfis(perfils).build();

        Usuario registered = usuarioService.salvar(usuario);

        if (registered != null) {
            URI location = uriBuilder.path("/{id}").buildAndExpand(registered.getId()).toUri();
            return ResponseEntity.created(location).body(RegisterResponse.builder().id(1L).usuario(registered).build());
        }

        throw new ResourceFoundException("Usuário não cadastrado!");
    }


}