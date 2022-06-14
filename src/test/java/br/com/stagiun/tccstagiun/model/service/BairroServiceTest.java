package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Bairro;
import br.com.stagiun.tccstagiun.model.repository.BairroRepository;
import br.com.stagiun.tccstagiun.model.service.impl.BairroServiceImpl;
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
public class BairroServiceTest {

    @InjectMocks
    BairroServiceImpl bairroService;

    @Mock
    BairroRepository bairroRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Bairro> list = new ArrayList<>();

        Bairro bairro = domainMock.getBairro();
        Bairro bairro1 = domainMock.getBairro();
        Bairro bairro2 = domainMock.getBairro();

        list.add(bairro);
        list.add(bairro1);
        list.add(bairro2);

        when(bairroRepository.findAll()).thenReturn(list);

        // test
        List<Bairro> urls = bairroService.list();

        assertEquals(3, urls.size());
        verify(bairroRepository, times(1)).findAll();
    }

    @Test
    public void getBairroByIdTest() {
        when(bairroRepository.findById(1L)).thenReturn(Optional.of(domainMock.getBairro()));

        Optional<Bairro> bairro = bairroService.findById(1L);

        assertEquals("AP", bairro.get().getDescricao());
    }

    @Test
    public void getFindBairroByShortIdTest() {
        Bairro bairro = domainMock.getBairro();
        when(bairroService.findById(1L)).thenReturn(Optional.ofNullable(bairro));

        Optional<Bairro> result = bairroService.findById(1L);

        assertEquals("AP", bairro.getDescricao());
    }

    @Test
    public void createBairroTest() throws ResourceFoundException {
        Bairro url = domainMock.getBairro();
        bairroService.salvar(url);

        verify(bairroRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreBairroTest() throws ResourceFoundException {
        Bairro bairro = domainMock.getBairro();
        bairroService.salvar(bairro);

        when(bairroService.salvar(bairro)).thenReturn(bairro);
        Bairro result = bairroService.salvar(bairro);

        assertEquals("AP", result.getDescricao());
    }
}
