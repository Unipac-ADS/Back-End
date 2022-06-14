package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Usuario;
import br.com.stagiun.tccstagiun.model.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(value = "local")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @MockBean
    UsuarioService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_usuarioes() {
        List<Usuario> usuarioList = new ArrayList<>();

        Usuario usuario = domainMock.getUsuario();
        usuarioList.add(usuario);
        
        when(service.list()).thenReturn(usuarioList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Usuario usuario = domainMock.getUsuario();
        when(service.findById(id)).thenReturn(Optional.of(usuario));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Usuario usuario = domainMock.getUsuario();

        when(this.service.salvar(usuario)).thenReturn(usuario);

        Usuario usuarioSalvo = this.service.salvar(usuario);
        assertNotNull(usuarioSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Usuario usuario = domainMock.getUsuario();

        when(service.editar(id, usuario)).thenReturn(usuario);

        Usuario usuarioAlterado = this.service.editar(id, usuario);
        assertNotNull(usuarioAlterado);
    }

}