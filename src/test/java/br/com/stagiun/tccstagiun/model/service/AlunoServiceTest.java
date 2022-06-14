package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Aluno;
import br.com.stagiun.tccstagiun.model.repository.AlunoRepository;
import br.com.stagiun.tccstagiun.model.service.impl.AlunoServiceImpl;
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
public class AlunoServiceTest {

    @InjectMocks
    AlunoServiceImpl alunoService;

    @Mock
    AlunoRepository alunoRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Aluno> list = new ArrayList<>();

        Aluno aluno = domainMock.getAluno();
        Aluno aluno1 = domainMock.getAluno1();
        Aluno aluno2 = domainMock.getAluno2();
        Aluno aluno3 = domainMock.getAluno2();

        list.add(aluno);
        list.add(aluno1);
        list.add(aluno2);
        list.add(aluno3);

        when(alunoRepository.findAll()).thenReturn(list);

        // test
        List<Aluno> urls = alunoService.list();

        assertEquals(4, urls.size());
        verify(alunoRepository, times(1)).findAll();
    }

    @Test
    public void getAlunoByIdTest() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(domainMock.getAluno()));

        Optional<Aluno> aluno = alunoService.findById(1L);

        assertEquals("Maria", aluno.get().getNome());
        assertEquals("ADS001", aluno.get().getMatricula());
        assertEquals(1245683215, aluno.get().getCpf());
        assertEquals(32564748, aluno.get().getTelefone());
        assertEquals("brteste@hotmail.com", aluno.get().getEmail());
        assertEquals("cvteste1", aluno.get().getCurriculo());
    }

    @Test
    public void getFindAlunoByShortIdTest() {
        Aluno aluno = domainMock.getAluno();
        when(alunoService.findById(1l)).thenReturn(Optional.ofNullable(aluno));

        Optional<Aluno> result = alunoService.findById(1l);

        assertEquals("Maria", aluno.getNome());
        assertEquals("ADS001", aluno.getMatricula());
        assertEquals(1245683215, aluno.getCpf());
        assertEquals(32564748, aluno.getTelefone());
        assertEquals("brteste@hotmail.com", aluno.getEmail());
        assertEquals("cvteste1", aluno.getCurriculo());
    }

    @Test
    public void createAlunoTest() throws ResourceFoundException {
        Aluno url = domainMock.getAluno();
        alunoService.salvar(url);

        verify(alunoRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreAlunoTest() throws ResourceFoundException {
        Aluno aluno = domainMock.getAluno();
        alunoService.salvar(aluno);

        when(alunoService.salvar(aluno)).thenReturn(aluno);
        Aluno result = alunoService.salvar(aluno);

        assertEquals("Maria", result.getNome());
        assertEquals("ADS001", result.getMatricula());
        assertEquals(1245683215, result.getCpf());
        assertEquals(32564748, result.getTelefone());
        assertEquals("brteste@hotmail.com", result.getEmail());
        assertEquals("cvteste1", result.getCurriculo());
    }
}
