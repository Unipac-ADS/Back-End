package br.com.stagiun.tccstagiun.services;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
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

    //public ExpectedException thrown = ExpectedException.none();

    private Turma getTurma() {
        return Turma.builder()
                .descricao("ADS")
                .periodo(5)
                .build();
    }

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Turma> list = new ArrayList<>();

        Turma turma = getTurma();
        Turma turma1 = getTurma();
        Turma turma2 = getTurma();

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
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(getTurma()));

        Optional<Turma> turma = turmaService.findById(1L);

        assertEquals("ADS", turma.get().getDescricao());
        assertEquals(5, turma.get().getPeriodo());
    }

    @Test
    public void getFindTurmaByShortIdTest() {
        Turma turma = getTurma();
        when(turmaService.findById(1L)).thenReturn(Optional.ofNullable(turma));

        Optional<Turma> result = turmaService.findById(1L);

        assertEquals("ADS", turma.getDescricao());
        assertEquals(5, turma.getPeriodo());
    }

    @Test
    public void createTurmaTest() throws ResourceFoundException {
        Turma url = getTurma();
        turmaService.salvar(url);

        verify(turmaRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreTurmaTest() throws ResourceFoundException {
        Turma turma = getTurma();
        turmaService.salvar(turma);

        when(turmaService.salvar(turma)).thenReturn(turma);
        Turma result = turmaService.salvar(turma);

        assertEquals("ADS", result.getDescricao());
        assertEquals(5, result.getPeriodo());
    }
}
