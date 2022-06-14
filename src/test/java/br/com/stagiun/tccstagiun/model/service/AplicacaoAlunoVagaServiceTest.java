package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.AplicacaoAlunoVaga;
import br.com.stagiun.tccstagiun.model.repository.AplicacaoAlunoVagaRepository;
import br.com.stagiun.tccstagiun.model.service.impl.AplicacaoAlunoVagaImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(value = "local")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Profile("test")
public class AplicacaoAlunoVagaServiceTest {

    @InjectMocks
    AplicacaoAlunoVagaImpl aplicacaoAlunoVagaService;

    @Mock
    AplicacaoAlunoVagaRepository aplicacaoAlunoVagaRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<AplicacaoAlunoVaga> list = new ArrayList<>();

        AplicacaoAlunoVaga aplicacaoAlunoVaga = domainMock.getAplicacaoAlunoVaga();
        AplicacaoAlunoVaga aplicacaoAlunoVaga1 = domainMock.getAplicacaoAlunoVaga();
        AplicacaoAlunoVaga aplicacaoAlunoVaga2 = domainMock.getAplicacaoAlunoVaga();

        list.add(aplicacaoAlunoVaga);
        list.add(aplicacaoAlunoVaga1);
        list.add(aplicacaoAlunoVaga2);

        when(aplicacaoAlunoVagaRepository.findAll()).thenReturn(list);

        // test
        List<AplicacaoAlunoVaga> urls = aplicacaoAlunoVagaService.list();

        assertEquals(3, urls.size());
        verify(aplicacaoAlunoVagaRepository, times(1)).findAll();
    }

    @Test
    public void getAplicacaoAlunoVagaByIdTest() {
        when(aplicacaoAlunoVagaRepository.findById(1L)).thenReturn(Optional.of(domainMock.getAplicacaoAlunoVaga()));

        Optional<AplicacaoAlunoVaga> aplicacaoAlunoVaga = aplicacaoAlunoVagaService.findById(1L);

        assertEquals("30/04/2022", aplicacaoAlunoVaga.get().getDataAplicacao());

    }

    @Test
    public void getFindAplicacaoAlunoVagaByShortIdTest() {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = domainMock.getAplicacaoAlunoVaga();
        when(aplicacaoAlunoVagaService.findById(1L)).thenReturn(Optional.ofNullable(aplicacaoAlunoVaga));

        Optional<AplicacaoAlunoVaga> result = aplicacaoAlunoVagaService.findById(1L);

        assertEquals("30/04/2022", aplicacaoAlunoVaga.getDataAplicacao());
    }

    @Test
    public void createAplicacaoAlunoVagaTest() throws ResourceFoundException {
        AplicacaoAlunoVaga url = domainMock.getAplicacaoAlunoVaga();
        aplicacaoAlunoVagaService.salvar(url);

        verify(aplicacaoAlunoVagaRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreAplicacaoAlunoVagaTest() throws ResourceFoundException {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = domainMock.getAplicacaoAlunoVaga();
        aplicacaoAlunoVagaService.salvar(aplicacaoAlunoVaga);

        when(aplicacaoAlunoVagaService.salvar(aplicacaoAlunoVaga)).thenReturn(aplicacaoAlunoVaga);
        AplicacaoAlunoVaga result = aplicacaoAlunoVagaService.salvar(aplicacaoAlunoVaga);

        assertEquals("30/04/2022", result.getDataAplicacao());
    }
}
