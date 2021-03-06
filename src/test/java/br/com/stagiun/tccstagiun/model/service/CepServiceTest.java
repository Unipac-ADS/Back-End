package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Cep;
import br.com.stagiun.tccstagiun.model.repository.CepRepository;
import br.com.stagiun.tccstagiun.model.service.impl.CepServiceImpl;
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
public class CepServiceTest {

    @InjectMocks
    CepServiceImpl cepService;

    @Mock
    CepRepository cepRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();
    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Cep> list = new ArrayList<>();

        Cep cep = domainMock.getCep();
        Cep cep1 = domainMock.getCep();
        Cep cep2 = domainMock.getCep();

        list.add(cep);
        list.add(cep1);
        list.add(cep2);

        when(cepRepository.findAll()).thenReturn(list);

        // test
        List<Cep> urls = cepService.list();

        assertEquals(3, urls.size());
        verify(cepRepository, times(1)).findAll();
    }

    @Test
    public void getCepByIdTest() {
        when(cepRepository.findById(1L)).thenReturn(Optional.of(domainMock.getCep()));

        Optional<Cep> cep = cepService.findById(1L);

        assertEquals("48688-125", cep.get().getDescricao());
    }

    @Test
    public void getFindCepByShortIdTest() {
        Cep cep = domainMock.getCep();
        when(cepService.findById(1L)).thenReturn(Optional.ofNullable(cep));

        Optional<Cep> result = cepService.findById(1L);

        assertEquals("48688-125", cep.getDescricao());
    }

    @Test
    public void createCepTest() throws ResourceFoundException {
        Cep cep = domainMock.getCep();
        cepService.salvar(cep);

        verify(cepRepository, times(3)).save(cep);
    }

    @Test
    public void createAndStoreCepTest() throws ResourceFoundException {
        Cep cep = domainMock.getCep();
        cepService.salvar(cep);

        when(cepService.salvar(cep)).thenReturn(cep);
        Cep result = cepService.salvar(cep);

        assertEquals("48688-125", result.getDescricao());
    }
}
