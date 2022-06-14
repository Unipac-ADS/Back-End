package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Cidade;
import br.com.stagiun.tccstagiun.model.repository.CidadeRepository;
import br.com.stagiun.tccstagiun.model.service.impl.CidadeServiceImpl;
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
public class CidadeServiceTest {

    @InjectMocks
    CidadeServiceImpl cidadeService;

    @Mock
    CidadeRepository cidadeRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Cidade> list = new ArrayList<>();

        Cidade cidade = domainMock.getCidade();
        Cidade cidade1 = domainMock.getCidade();
        Cidade cidade2 = domainMock.getCidade();

        list.add(cidade);
        list.add(cidade1);
        list.add(cidade2);

        when(cidadeRepository.findAll()).thenReturn(list);

        // test
        List<Cidade> urls = cidadeService.list();

        assertEquals(3, urls.size());
        verify(cidadeRepository, times(1)).findAll();
    }

    @Test
    public void getCidadeByIdTest() {
        when(cidadeRepository.findById(1L)).thenReturn(Optional.of(domainMock.getCidade()));

        Optional<Cidade> cidade = cidadeService.findById(1L);

        assertEquals("Uberlândia", cidade.get().getDescricao());
    }

    @Test
    public void getFindCidadeByShortIdTest() {
        Cidade cidade = domainMock.getCidade();
        when(cidadeService.findById(1L)).thenReturn(Optional.ofNullable(cidade));

        Optional<Cidade> result = cidadeService.findById(1L);

        assertEquals("Uberlândia", cidade.getDescricao());
    }

    @Test
    public void createCidadeTest() throws ResourceFoundException {
        Cidade url = domainMock.getCidade();
        cidadeService.salvar(url);

        verify(cidadeRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreCidadeTest() throws ResourceFoundException {
        Cidade cidade = domainMock.getCidade();
        cidadeService.salvar(cidade);

        when(cidadeService.salvar(cidade)).thenReturn(cidade);
        Cidade result = cidadeService.salvar(cidade);

        assertEquals("Uberlândia", result.getDescricao());
    }
}
