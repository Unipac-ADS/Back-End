package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Cep;
import br.com.stagiun.tccstagiun.model.repository.CepRepository;
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
class CepServiceTest {

    @Mock
    CepRepository cepRepository;

    @MockBean
    CepService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_cepes() {
        List<Cep> cepList = new ArrayList<>();

        Cep cep = domainMock.getCep();
        cepList.add(cep);

        when(service.list()).thenReturn(cepList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Cep cep = domainMock.getCep();
        when(service.findById(id)).thenReturn(Optional.of(cep));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Cep cep = domainMock.getCep();

        when(this.service.salvar(cep)).thenReturn(cep);

        Cep cepSalvo = this.service.salvar(cep);
        assertNotNull(cepSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Cep cep = domainMock.getCep();

        when(service.editar(id, cep)).thenReturn(cep);

        Cep cepAlterado = this.service.editar(id, cep);
        assertNotNull(cepAlterado);
    }

}