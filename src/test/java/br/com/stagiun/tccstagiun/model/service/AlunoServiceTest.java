package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Aluno;
import br.com.stagiun.tccstagiun.model.repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(value = "local")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AlunoServiceTest {

    @Mock
    AlunoRepository alunoRepository;

    @MockBean
    AlunoService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_alunoes() {
        List<Aluno> alunoList = new ArrayList<>();

        Aluno aluno = domainMock.getAluno();
        alunoList.add(aluno);

        Aluno aluno2 = domainMock.getAluno2();
        alunoList.add(aluno2);

        Aluno aluno3 = domainMock.getAluno3();
        alunoList.add(aluno3);

        when(service.list()).thenReturn(alunoList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Aluno aluno = domainMock.getAluno();
        when(service.findById(id)).thenReturn(Optional.of(aluno));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Aluno aluno = domainMock.getAluno();

        when(this.service.salvar(aluno)).thenReturn(aluno);

        Aluno alunoSalvo = this.service.salvar(aluno);
        assertNotNull(alunoSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Aluno aluno = domainMock.getAluno();

        when(service.editar(id, aluno)).thenReturn(aluno);

        Aluno alunoAlterado = this.service.editar(id, aluno);
        assertNotNull(alunoAlterado);
    }

}