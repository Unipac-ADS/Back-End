package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Estado;
import br.com.stagiun.tccstagiun.model.domain.Pais;
import br.com.stagiun.tccstagiun.model.repository.EstadoRepository;
import br.com.stagiun.tccstagiun.model.service.impl.EstadoServiceImpl;
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
public class EstadoServiceTest {

    @InjectMocks
    EstadoServiceImpl estadoService;

    @Mock
    EstadoRepository estadoRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Estado> list = new ArrayList<>();

        Estado estado = domainMock.getEstado();
        Estado estado1 = domainMock.getEstado();
        Estado estado2 = domainMock.getEstado();

        list.add(estado);
        list.add(estado1);
        list.add(estado2);

        when(estadoRepository.findAll()).thenReturn(list);

        // test
        List<Estado> urls = estadoService.list();

        assertEquals(3, urls.size());
        verify(estadoRepository, times(1)).findAll();
    }

    @Test
    public void getEstadoByIdTest() {
        when(estadoRepository.findById(1L)).thenReturn(Optional.of(domainMock.getEstado()));

        Optional<Estado> estado = estadoService.findById(1L);

        assertEquals("Minas Gerais", estado.get().getDescricao());
        assertEquals("Brasil", estado.get().getPais().getDescricao());
    }

    @Test
    public void getFindEstadoByShortIdTest() {
        Estado estado = domainMock.getEstado();
        when(estadoService.findById(1L)).thenReturn(Optional.ofNullable(estado));

        Optional<Estado> result = estadoService.findById(1L);

        assertEquals("Minas Gerais", estado.getDescricao());
        assertEquals("Brasil", estado.getPais().getDescricao());
    }

    @Test
    public void createEstadoTest() throws ResourceFoundException {
        Estado url = domainMock.getEstado();
        estadoService.salvar(url);

        verify(estadoRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreEstadoTest() throws ResourceFoundException {
        Estado estado = domainMock.getEstado();
        estadoService.salvar(estado);

        when(estadoService.salvar(estado)).thenReturn(estado);
        Estado result = estadoService.salvar(estado);

        assertEquals("Minas Gerais", result.getDescricao());
        assertEquals("Brasil", result.getPais().getDescricao());
    }
}
