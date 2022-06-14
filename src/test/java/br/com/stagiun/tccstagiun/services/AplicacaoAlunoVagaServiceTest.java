package br.com.stagiun.tccstagiun.services;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
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
public class AplicacaoAlunoVagaServiceTest {

    @InjectMocks
    AplicacaoAlunoVagaImpl aplicacaoAlunoVagaService;

    @Mock
    AplicacaoAlunoVagaRepository aplicacaoAlunoVagaRepository;

    //public ExpectedException thrown = ExpectedException.none();

    private AplicacaoAlunoVaga getAplicacaoAlunoVaga() {
        return AplicacaoAlunoVaga.builder()
                .dataAplicacao("30/04/2022")
                .build();
    }

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<AplicacaoAlunoVaga> list = new ArrayList<>();

        AplicacaoAlunoVaga aplicacaoAlunoVaga = getAplicacaoAlunoVaga();
        AplicacaoAlunoVaga aplicacaoAlunoVaga1 = getAplicacaoAlunoVaga();
        AplicacaoAlunoVaga aplicacaoAlunoVaga2 = getAplicacaoAlunoVaga();

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
        when(aplicacaoAlunoVagaRepository.findById(1L)).thenReturn(Optional.of(getAplicacaoAlunoVaga()));

        Optional<AplicacaoAlunoVaga> aplicacaoAlunoVaga = aplicacaoAlunoVagaService.findById(1L);

        assertEquals("30/04/2022", aplicacaoAlunoVaga.get().getDataAplicacao());

    }

    @Test
    public void getFindAplicacaoAlunoVagaByShortIdTest() {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = getAplicacaoAlunoVaga();
        when(aplicacaoAlunoVagaService.findById(1L)).thenReturn(Optional.ofNullable(aplicacaoAlunoVaga));

        Optional<AplicacaoAlunoVaga> result = aplicacaoAlunoVagaService.findById(1L);

        assertEquals("30/04/2022", aplicacaoAlunoVaga.getDataAplicacao());
    }

    @Test
    public void createAplicacaoAlunoVagaTest() throws ResourceFoundException {
        AplicacaoAlunoVaga url = getAplicacaoAlunoVaga();
        aplicacaoAlunoVagaService.salvar(url);

        verify(aplicacaoAlunoVagaRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreAplicacaoAlunoVagaTest() throws ResourceFoundException {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = getAplicacaoAlunoVaga();
        aplicacaoAlunoVagaService.salvar(aplicacaoAlunoVaga);

        when(aplicacaoAlunoVagaService.salvar(aplicacaoAlunoVaga)).thenReturn(aplicacaoAlunoVaga);
        AplicacaoAlunoVaga result = aplicacaoAlunoVagaService.salvar(aplicacaoAlunoVaga);

        assertEquals("30/04/2022", result.getDataAplicacao());
    }
}
