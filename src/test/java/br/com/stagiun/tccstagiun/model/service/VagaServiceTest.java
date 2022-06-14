package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Vaga;
import br.com.stagiun.tccstagiun.model.repository.VagaRepository;
import br.com.stagiun.tccstagiun.model.service.impl.VagaServiceImpl;
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
public class VagaServiceTest {

    @InjectMocks
    VagaServiceImpl vagaService;

    @Mock
    VagaRepository vagaRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Vaga> list = new ArrayList<>();

        Vaga vaga = domainMock.getVaga();
        Vaga vaga1 = domainMock.getVaga();
        Vaga vaga2 = domainMock.getVaga();

        list.add(vaga);
        list.add(vaga1);
        list.add(vaga2);

        when(vagaRepository.findAll()).thenReturn(list);

        // test
        List<Vaga> urls = vagaService.list();

        assertEquals(3, urls.size());
        verify(vagaRepository, times(1)).findAll();
    }

    @Test
    public void getPerfilByIdTest() {
        when(vagaRepository.findById(1L)).thenReturn(Optional.of(domainMock.getVaga()));

        Optional<Vaga> vaga = vagaService.findById(1L);

        assertEquals(1, vaga.get().getId());
        assertEquals(333.00, vaga.get().getAmount());
        assertEquals("26/05/2022", vaga.get().getDataOfertaInicio());
        assertEquals("30/05/2022", vaga.get().getDataOfertaFim());
        assertEquals("Vaga Desenvolvedor Java", vaga.get().getNome());
    }

    @Test
    public void getFindPerfilByShortIdTest() {
        Vaga vaga = domainMock.getVaga();
        when(vagaService.findById(1L)).thenReturn(Optional.ofNullable(vaga));

        Optional<Vaga> result = vagaService.findById(1L);

        assertEquals(1, vaga.getId());
        assertEquals(333.00, vaga.getAmount());
        assertEquals("26/05/2022", vaga.getDataOfertaInicio());
        assertEquals("30/05/2022", vaga.getDataOfertaFim());
        assertEquals("Vaga Desenvolvedor Java", vaga.getNome());
    }

    @Test
    public void createPerfilTest() throws ResourceFoundException {
        Vaga url = domainMock.getVaga();
        vagaService.salvar(url);

        verify(vagaRepository, times(1)).save(url);
    }

    @Test
    public void createAndStorePerfilTest() throws ResourceFoundException {
        Vaga vaga = domainMock.getVaga();
        vagaService.salvar(vaga);

        when(vagaService.salvar(vaga)).thenReturn(vaga);
        Vaga result = vagaService.salvar(vaga);

        assertEquals(1, result.getId());
        assertEquals(333.00, result.getAmount());
        assertEquals("26/05/2022", result.getDataOfertaInicio());
        assertEquals("30/05/2022", result.getDataOfertaFim());
        assertEquals("Vaga Desenvolvedor Java", result.getNome());
    }
}
