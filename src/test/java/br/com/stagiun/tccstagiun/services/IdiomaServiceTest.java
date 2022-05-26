package br.com.stagiun.tccstagiun.services;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Idioma;
import br.com.stagiun.tccstagiun.model.repository.IdiomaRepository;
import br.com.stagiun.tccstagiun.model.service.impl.IdiomaServiceImpl;
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
public class IdiomaServiceTest {

    @InjectMocks
    IdiomaServiceImpl idiomaService;

    @Mock
    IdiomaRepository idiomaRepository;

    //public ExpectedException thrown = ExpectedException.none();

    private Idioma getIdioma() {
        return Idioma.builder()
                .nome("Inglês")
                .build();
    }

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Idioma> list = new ArrayList<>();

        Idioma idioma = getIdioma();
        Idioma idioma1 = getIdioma();
        Idioma idioma2 = getIdioma();

        list.add(idioma);
        list.add(idioma1);
        list.add(idioma2);

        when(idiomaRepository.findAll()).thenReturn(list);

        // test
        List<Idioma> urls = idiomaService.list();

        assertEquals(3, urls.size());
        verify(idiomaRepository, times(1)).findAll();
    }

    @Test
    public void getIdiomaByIdTest() {
        when(idiomaRepository.findById(1L)).thenReturn(Optional.of(getIdioma()));

        Optional<Idioma> habilidade = idiomaService.findById(1L);

        assertEquals("Inglês", habilidade.get().getNome());
    }

    @Test
    public void getFindIdiomaByShortIdTest() {
        Idioma idioma = getIdioma();
        when(idiomaService.findById(1L)).thenReturn(Optional.ofNullable(idioma));

        Optional<Idioma> result = idiomaService.findById(1L);

        assertEquals("Inglês", idioma.getNome());
    }

    @Test
    public void createIdiomaTest() throws ResourceFoundException {
        Idioma url = getIdioma();
        idiomaService.salvar(url);

        verify(idiomaRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreIdiomaTest() throws ResourceFoundException {
        Idioma idioma = getIdioma();
        idiomaService.salvar(idioma);

        when(idiomaService.salvar(idioma)).thenReturn(idioma);
        Idioma result = idiomaService.salvar(idioma);

        assertEquals("Inglês", result.getNome());
    }
}
