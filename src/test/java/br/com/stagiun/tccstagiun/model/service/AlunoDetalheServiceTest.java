package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhe;
import br.com.stagiun.tccstagiun.model.repository.AlunoDetalheRepository;
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
class AlunoDetalheServiceTest {

    @Mock
    AlunoDetalheRepository alunoDetalheRepository;

    @MockBean
    AlunoDetalheService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_alunoDetalhees() {
        List<AlunoDetalhe> alunoDetalheList = new ArrayList<>();

        AlunoDetalhe alunoDetalhe = domainMock.getAlunoDetalhe();
        alunoDetalheList.add(alunoDetalhe);

        when(service.list()).thenReturn(alunoDetalheList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        AlunoDetalhe alunoDetalhe = domainMock.getAlunoDetalhe();
        when(service.findById(id)).thenReturn(Optional.of(alunoDetalhe));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        AlunoDetalhe alunoDetalhe = domainMock.getAlunoDetalhe();

        when(this.service.salvar(alunoDetalhe)).thenReturn(alunoDetalhe);

        AlunoDetalhe alunoDetalheSalvo = this.service.salvar(alunoDetalhe);
        assertNotNull(alunoDetalheSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        AlunoDetalhe alunoDetalhe = domainMock.getAlunoDetalhe();

        when(service.editar(id, alunoDetalhe)).thenReturn(alunoDetalhe);

        AlunoDetalhe alunoDetalheAlterado = this.service.editar(id, alunoDetalhe);
        assertNotNull(alunoDetalheAlterado);
    }

}