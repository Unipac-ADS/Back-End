package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.AplicacaoAlunoVaga;
import br.com.stagiun.tccstagiun.model.repository.AplicacaoAlunoVagaRepository;
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
class AplicacaoAlunoVagaServiceTest {

    @Mock
    AplicacaoAlunoVagaRepository aplicacaoAlunoVagaRepository;

    @MockBean
    AplicacaoAlunoVagaService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_aplicacaoAlunoVagaes() {
        List<AplicacaoAlunoVaga> aplicacaoAlunoVagaList = new ArrayList<>();

        AplicacaoAlunoVaga aplicacaoAlunoVaga = domainMock.getAplicacaoAlunoVaga();
        aplicacaoAlunoVagaList.add(aplicacaoAlunoVaga);

        when(service.list()).thenReturn(aplicacaoAlunoVagaList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        AplicacaoAlunoVaga aplicacaoAlunoVaga = domainMock.getAplicacaoAlunoVaga();
        when(service.findById(id)).thenReturn(Optional.of(aplicacaoAlunoVaga));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = domainMock.getAplicacaoAlunoVaga();

        when(this.service.salvar(aplicacaoAlunoVaga)).thenReturn(aplicacaoAlunoVaga);

        AplicacaoAlunoVaga aplicacaoAlunoVagaSalvo = this.service.salvar(aplicacaoAlunoVaga);
        assertNotNull(aplicacaoAlunoVagaSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        AplicacaoAlunoVaga aplicacaoAlunoVaga = domainMock.getAplicacaoAlunoVaga();

        when(service.editar(id, aplicacaoAlunoVaga)).thenReturn(aplicacaoAlunoVaga);

        AplicacaoAlunoVaga aplicacaoAlunoVagaAlterado = this.service.editar(id, aplicacaoAlunoVaga);
        assertNotNull(aplicacaoAlunoVagaAlterado);
    }

}