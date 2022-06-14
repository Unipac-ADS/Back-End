package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Curso;
import br.com.stagiun.tccstagiun.model.repository.CursoRepository;
import br.com.stagiun.tccstagiun.model.service.impl.CursoServiceImpl;
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
public class CursoServiceTest {

    @InjectMocks
    CursoServiceImpl cursoService;

    @Mock
    CursoRepository cursoRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Curso> list = new ArrayList<>();

        Curso curso = domainMock.getCurso();
        Curso curso1 = domainMock.getCurso();
        Curso curso2 = domainMock.getCurso();

        list.add(curso);
        list.add(curso1);
        list.add(curso2);

        when(cursoRepository.findAll()).thenReturn(list);

        // test
        List<Curso> urls = cursoService.list();

        assertEquals(3, urls.size());
        verify(cursoRepository, times(1)).findAll();
    }

    @Test
    public void getCursoByIdTest() {
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(domainMock.getCurso()));

        Optional<Curso> curso = cursoService.findById(1L);

        assertEquals("Análise e Desenvolvimento de Sistemas", curso.get().getDescricao());
    }

    @Test
    public void getFindCursoByShortIdTest() {
        Curso curso = domainMock.getCurso();
        when(cursoService.findById(1L)).thenReturn(Optional.ofNullable(curso));

        Optional<Curso> result = cursoService.findById(1L);

        assertEquals("Análise e Desenvolvimento de Sistemas", curso.getDescricao());
    }

    @Test
    public void createCursoTest() throws ResourceFoundException {
        Curso url = domainMock.getCurso();
        cursoService.salvar(url);

        verify(cursoRepository, times(3)).save(url);
    }

    @Test
    public void createAndStoreCursoTest() throws ResourceFoundException {
        Curso curso = domainMock.getCurso();
        cursoService.salvar(curso);

        when(cursoService.salvar(curso)).thenReturn(curso);
        Curso result = cursoService.salvar(curso);

        assertEquals("Análise e Desenvolvimento de Sistemas", result.getDescricao());
    }
}
