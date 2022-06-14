package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Dica;
import br.com.stagiun.tccstagiun.model.repository.DicaRepository;
import br.com.stagiun.tccstagiun.model.service.impl.DicaImpl;
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
public class DicaServiceTest {

    @InjectMocks
    DicaImpl dicaService;

    @Mock
    DicaRepository dicaRepository;

    //public ExpectedException thrown = ExpectedException.none();

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Dica> list = new ArrayList<>();

        Dica dica = domainMock.getDica();
        Dica dica1 = domainMock.getDica();
        Dica dica2 = domainMock.getDica();

        list.add(dica);
        list.add(dica1);
        list.add(dica2);

        when(dicaRepository.findAll()).thenReturn(list);

        // test
        List<Dica> urls = dicaService.list();

        assertEquals(3, urls.size());
        verify(dicaRepository, times(1)).findAll();
    }

    @Test
    public void getDicaByIdTest() {
        when(dicaRepository.findById(1L)).thenReturn(Optional.of(domainMock.getDica()));

        Optional<Dica> dica = dicaService.findById(1L);

        assertEquals("Como virar um ninja em Java", dica.get().getTitulo());
        assertEquals("Aprenda o que estudar para se tornar um ninja", dica.get().getDescricao());
        assertEquals("links", dica.get().getLinksUteis());
        assertEquals("25/05/2022", dica.get().getDataPublicacao());
    }

    @Test
    public void getFindDicaByShortIdTest() {
        Dica dica = domainMock.getDica();
        when(dicaService.findById(1L)).thenReturn(Optional.ofNullable(dica));

        Optional<Dica> result = dicaService.findById(1L);

        assertEquals("Como virar um ninja em Java", dica.getTitulo());
        assertEquals("Aprenda o que estudar para se tornar um ninja", dica.getDescricao());
        assertEquals("links", dica.getLinksUteis());
        assertEquals("25/05/2022", dica.getDataPublicacao());
    }

    @Test
    public void createDicaTest() throws ResourceFoundException {
        Dica url = domainMock.getDica();
        dicaService.salvar(url);

        verify(dicaRepository, times(3)).save(url);
    }

    @Test
    public void createAndStoreDicaTest() throws ResourceFoundException {
        Dica curso = domainMock.getDica();
        dicaService.salvar(curso);

        when(dicaService.salvar(curso)).thenReturn(curso);
        Dica result = dicaService.salvar(curso);

        assertEquals("Como virar um ninja em Java", result.getTitulo());
        assertEquals("Aprenda o que estudar para se tornar um ninja", result.getDescricao());
        assertEquals("links", result.getLinksUteis());
        assertEquals("25/05/2022", result.getDataPublicacao());
    }
}
