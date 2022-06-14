package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Turma;
import br.com.stagiun.tccstagiun.model.repository.TurmaRepository;
import br.com.stagiun.tccstagiun.model.service.impl.TurmaServiceImpl;
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
public class TurmaServiceTest {

    @InjectMocks
    TurmaServiceImpl turmaService;

    @Mock
    TurmaRepository turmaRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Turma> list = new ArrayList<>();

        Turma turma = domainMock.getTurma();
        Turma turma1 = domainMock.getTurma();
        Turma turma2 = domainMock.getTurma();

        list.add(turma);
        list.add(turma1);
        list.add(turma2);

        when(turmaRepository.findAll()).thenReturn(list);

        // test
        List<Turma> urls = turmaService.list();

        assertEquals(3, urls.size());
        verify(turmaRepository, times(1)).findAll();
    }

    @Test
    public void getTurmaByIdTest() {
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(domainMock.getTurma()));

        Optional<Turma> turma = turmaService.findById(1L);

        assertEquals("ADS", turma.get().getDescricao());
        assertEquals(5, turma.get().getPeriodo());
    }

    @Test
    public void getFindTurmaByShortIdTest() {
        Turma turma = domainMock.getTurma();
        when(turmaService.findById(1L)).thenReturn(Optional.ofNullable(turma));

        Optional<Turma> result = turmaService.findById(1L);

        assertEquals("ADS", turma.getDescricao());
        assertEquals(5, turma.getPeriodo());
    }

    @Test
    public void createTurmaTest() throws ResourceFoundException {
        Turma url = domainMock.getTurma();
        turmaService.salvar(url);

        verify(turmaRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreTurmaTest() throws ResourceFoundException {
        Turma turma = domainMock.getTurma();
        turmaService.salvar(turma);

        when(turmaService.salvar(turma)).thenReturn(turma);
        Turma result = turmaService.salvar(turma);

        assertEquals("ADS", result.getDescricao());
        assertEquals(5, result.getPeriodo());
    }
}
