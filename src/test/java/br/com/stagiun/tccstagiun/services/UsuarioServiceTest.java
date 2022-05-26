package br.com.stagiun.tccstagiun.services;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Usuario;
import br.com.stagiun.tccstagiun.model.repository.UsuarioRepository;
import br.com.stagiun.tccstagiun.model.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioServiceTest {

    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @Mock
    UsuarioRepository usuarioRepository;

    //public ExpectedException thrown = ExpectedException.none();

    private Usuario getUsuario() {
        return Usuario.builder()
                .nome("Playboyzinho")
                .email("palyboyzinho@gmail.com")
                .senha("Mjolnir")
                .build();
    }

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Usuario> list = new ArrayList<>();

        Usuario usuario = getUsuario();
        Usuario usuario1 = getUsuario();
        Usuario usuario2 = getUsuario();

        list.add(usuario);
        list.add(usuario1);
        list.add(usuario2);

        when(usuarioRepository.findAll()).thenReturn(list);

        // test
        List<Usuario> urls = usuarioService.list();

        assertEquals(3, urls.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void getUsuarioByIdTest() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(getUsuario()));

        Optional<Usuario> usuario = usuarioService.findById(1L);

        assertEquals("Playboyzinho", usuario.get().getNome());
        assertEquals("palyboyzinho@gmail.com", usuario.get().getEmail());
        assertEquals("Mjolnir", usuario.get().getSenha());
    }

    @Test
    public void getFindUsuarioByShortIdTest() {
        Usuario usuario = getUsuario();
        when(usuarioService.findById(1L)).thenReturn(Optional.ofNullable(usuario));

        Optional<Usuario> result = usuarioService.findById(1L);

        assertEquals("Playboyzinho", usuario.getNome());
        assertEquals("palyboyzinho@gmail.com", usuario.getEmail());
        assertEquals("Mjolnir", usuario.getSenha());
    }

    @Test
    public void createUsuarioTest() throws ResourceFoundException {
        Usuario url = getUsuario();
        usuarioService.salvar(url);

        verify(usuarioRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreUsuarioTest() throws ResourceFoundException {
        Usuario usuario = getUsuario();
        usuarioService.salvar(usuario);

        when(usuarioService.salvar(usuario)).thenReturn(usuario);
        Usuario result = usuarioService.salvar(usuario);

        assertEquals("Playboyzinho", result.getNome());
        assertEquals("palyboyzinho@gmail.com", result.getEmail());
        assertEquals("Mjolnir", result.getSenha());
    }
}
