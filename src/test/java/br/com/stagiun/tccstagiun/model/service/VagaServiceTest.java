package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Vaga;
import br.com.stagiun.tccstagiun.model.repository.VagaRepository;
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
class VagaServiceTest {

    @Mock
    VagaRepository vagaRepository;

    @MockBean
    VagaService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_vagaes() {
        List<Vaga> vagaList = new ArrayList<>();

        Vaga vaga = domainMock.getVaga();
        vagaList.add(vaga);

        when(service.list()).thenReturn(vagaList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Vaga vaga = domainMock.getVaga();
        when(service.findById(id)).thenReturn(Optional.of(vaga));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Vaga vaga = domainMock.getVaga();

        when(this.service.salvar(vaga)).thenReturn(vaga);

        Vaga vagaSalvo = this.service.salvar(vaga);
        assertNotNull(vagaSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Vaga vaga = domainMock.getVaga();

        when(service.editar(id, vaga)).thenReturn(vaga);

        Vaga vagaAlterado = this.service.editar(id, vaga);
        assertNotNull(vagaAlterado);
    }

}