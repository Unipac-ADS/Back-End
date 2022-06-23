package br.com.stagiun.tccstagiun.controller;

import br.com.stagiun.tccstagiun.controller.assemble.ResourcesAssemble;
import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.filter.TokenResponse;
import br.com.stagiun.tccstagiun.model.domain.Usuario;
import br.com.stagiun.tccstagiun.model.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ResourcesAssemble.V_1 + "login")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController extends ResourcesAssemble {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody Usuario usuario) throws Exception {
        TokenResponse token = usuarioService.getLoginAndReturnToken(usuario);
        if (token != null) {
           return ResponseEntity.ok(token);
        }

        throw new ResourceFoundException("Usuário não cadastrado!");
    }
}