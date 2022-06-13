package br.com.stagiun.tccstagiun.services;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
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

    //public ExpectedException thrown = ExpectedException.none();

    private Aluno getAluno() {
        return Aluno.builder()
                .nome("Brasil")
                .matricula("ADS001")
                .cpf(1245683215L)
                .telefone(32564748L)
                .email("brteste@hotmail.com")
                .curriculo("cvteste1")
                .build();
    }

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Aluno> list = new ArrayList<>();

        Aluno aluno = getAluno();
        Aluno aluno1 = getAluno();
        Aluno aluno2 = getAluno();

        list.add(aluno);
        list.add(aluno1);
        list.add(aluno2);

        when(alunoRepository.findAll()).thenReturn(list);

        // test
        List<Aluno> urls = alunoService.list();

        assertEquals(3, urls.size());
        verify(alunoRepository, times(1)).findAll();
    }

    @Test
    public void getAlunoByIdTest() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(getAluno()));

        Optional<Aluno> aluno = alunoService.findById(1L);

        assertEquals("Brasil", aluno.get().getNome());
        assertEquals("ADS001", aluno.get().getMatricula());
        assertEquals(1245683215, aluno.get().getCpf());
        assertEquals(32564748, aluno.get().getTelefone());
        assertEquals("brteste@hotmail.com", aluno.get().getEmail());
        assertEquals("cvteste1", aluno.get().getCurriculo());
    }

    @Test
    public void getFindAlunoByShortIdTest() {
        Aluno aluno = getAluno();
        when(alunoService.findById(1l)).thenReturn(Optional.ofNullable(aluno));

        Optional<Aluno> result = alunoService.findById(1l);

        assertEquals("Brasil", aluno.getNome());
        assertEquals("ADS001", aluno.getMatricula());
        assertEquals(1245683215, aluno.getCpf());
        assertEquals(32564748, aluno.getTelefone());
        assertEquals("brteste@hotmail.com", aluno.getEmail());
        assertEquals("cvteste1", aluno.getCurriculo());
    }

    @Test
    public void createAlunoTest() throws ResourceFoundException {
        Aluno url = getAluno();
        alunoService.salvar(url);

        verify(alunoRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreAlunoTest() throws ResourceFoundException {
        Aluno aluno = getAluno();
        alunoService.salvar(aluno);

        when(alunoService.salvar(aluno)).thenReturn(aluno);
        Aluno result = alunoService.salvar(aluno);

        assertEquals("Brasil", result.getNome());
        assertEquals("ADS001", result.getMatricula());
        assertEquals(1245683215, result.getCpf());
        assertEquals(32564748, result.getTelefone());
        assertEquals("brteste@hotmail.com", result.getEmail());
        assertEquals("cvteste1", result.getCurriculo());
    }
}
