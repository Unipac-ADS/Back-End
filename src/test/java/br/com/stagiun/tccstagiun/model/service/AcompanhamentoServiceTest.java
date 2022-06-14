package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;
import br.com.stagiun.tccstagiun.model.repository.AcompanhamentoRepository;
import br.com.stagiun.tccstagiun.model.service.impl.AcompanhamentoImpl;
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
public class AcompanhamentoServiceTest {

    @InjectMocks
    AcompanhamentoImpl acompanhamentoService;

    @Mock
    AcompanhamentoRepository acompanhamentoRepository;

    //public ExpectedException thrown = ExpectedException.none();

    private Acompanhamento getAcompanhamento() {
        return Acompanhamento.builder()
                .dataAplicacao("26/05/2022")
                .build();
    }

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Acompanhamento> list = new ArrayList<>();

        Acompanhamento acompanhamento = getAcompanhamento();
        Acompanhamento acompanhamento1 = getAcompanhamento();
        Acompanhamento acompanhamento2 = getAcompanhamento();

        list.add(acompanhamento);
        list.add(acompanhamento1);
        list.add(acompanhamento2);

        when(acompanhamentoRepository.findAll()).thenReturn(list);

        // test
        List<Acompanhamento> urls = acompanhamentoService.list();

        assertEquals(3, urls.size());
        verify(acompanhamentoRepository, times(1)).findAll();
    }

    @Test
    public void getAcompanhamentoByIdTest() {
        when(acompanhamentoRepository.findById(1L)).thenReturn(Optional.of(getAcompanhamento()));

        Optional<Acompanhamento> acompanhamento = acompanhamentoService.findById(1L);

        assertEquals("26/05/2022", acompanhamento.get().getDataAplicacao());

    }

    @Test
    public void getFindAcompanhamentoByShortIdTest() {
        Acompanhamento acompanhamento = getAcompanhamento();
        when(acompanhamentoService.findById(1L)).thenReturn(Optional.ofNullable(acompanhamento));

        Optional<Acompanhamento> result = acompanhamentoService.findById(1L);

        assertEquals("26/05/2022", acompanhamento.getDataAplicacao());
    }

    @Test
    public void createAcompanhamentoTest() throws ResourceFoundException {
        Acompanhamento url = getAcompanhamento();
        acompanhamentoService.salvar(url);

        verify(acompanhamentoRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreAcompanhamentoTest() throws ResourceFoundException {
        Acompanhamento acompanhamento = getAcompanhamento();
        acompanhamentoService.salvar(acompanhamento);

        when(acompanhamentoService.salvar(acompanhamento)).thenReturn(acompanhamento);
        Acompanhamento result = acompanhamentoService.salvar(acompanhamento);

        assertEquals("26/05/2022", result.getDataAplicacao());
    }
}
