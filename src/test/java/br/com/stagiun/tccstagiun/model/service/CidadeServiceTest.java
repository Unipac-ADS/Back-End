package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Cidade;
import br.com.stagiun.tccstagiun.model.repository.CidadeRepository;
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
class CidadeServiceTest {

    @Mock
    CidadeRepository cidadeRepository;

    @MockBean
    CidadeService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_cidadees() {
        List<Cidade> cidadeList = new ArrayList<>();

        Cidade cidade = domainMock.getCidade();
        cidadeList.add(cidade);

        Cidade cidade2 = domainMock.getCidade2();
        cidadeList.add(cidade2);

        Cidade cidade3 = domainMock.getCidade3();
        cidadeList.add(cidade3);

        when(service.list()).thenReturn(cidadeList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Cidade cidade = domainMock.getCidade();
        when(service.findById(id)).thenReturn(Optional.of(cidade));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Cidade cidade = domainMock.getCidade();

        when(this.service.salvar(cidade)).thenReturn(cidade);

        Cidade cidadeSalvo = this.service.salvar(cidade);
        assertNotNull(cidadeSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Cidade cidade = domainMock.getCidade();

        when(service.editar(id, cidade)).thenReturn(cidade);

        Cidade cidadeAlterado = this.service.editar(id, cidade);
        assertNotNull(cidadeAlterado);
    }

}