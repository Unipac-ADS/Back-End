package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.model.domain.Usuario;
import br.com.stagiun.tccstagiun.model.repository.UsuarioRepository;
import br.com.stagiun.tccstagiun.model.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioServiceImplTest {

    @Test
    void salvar() {
        Usuario usuario = new Usuario();
        usuario.setNome("Fernanda Ferreira");
        usuario.setEmail("fernanda@gmail.com");
        usuario.setSenha("btr1234");

        UsuarioServiceImpl usuarioService = new UsuarioServiceImpl();

        usuarioService.salvar(usuario);

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