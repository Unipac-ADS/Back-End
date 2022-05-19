package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Usuario;
import br.com.stagiun.tccstagiun.model.repository.UsuarioRepository;
import br.com.stagiun.tccstagiun.model.service.UsuarioService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioServiceImplTest {

    @Disabled
    void salvar() {
        Usuario usuario = new Usuario();
        usuario.setNome("Fernanda Ferreira");
        usuario.setEmail("fernanda@gmail.com");
        usuario.setSenha("btr1234");

        UsuarioServiceImpl usuarioService = new UsuarioServiceImpl();

        try {
            usuarioService.salvar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void editar() {
    }

    @Test
    void list() {
    }

    @Test
    void findById() {
    }

    @Test
    void delete() {
    }
}