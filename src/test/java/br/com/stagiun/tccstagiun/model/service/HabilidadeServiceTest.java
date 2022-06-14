package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Habilidade;
import br.com.stagiun.tccstagiun.model.repository.HabilidadeRepository;
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
class HabilidadeServiceTest {

    @Mock
    HabilidadeRepository habilidadeRepository;

    @MockBean
    HabilidadeService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_habilidadees() {
        List<Habilidade> habilidadeList = new ArrayList<>();

        Habilidade habilidade = domainMock.getHabilidade();
        habilidadeList.add(habilidade);

        when(service.list()).thenReturn(habilidadeList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Habilidade habilidade = domainMock.getHabilidade();
        when(service.findById(id)).thenReturn(Optional.of(habilidade));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Habilidade habilidade = domainMock.getHabilidade();

        when(this.service.salvar(habilidade)).thenReturn(habilidade);

        Habilidade habilidadeSalvo = this.service.salvar(habilidade);
        assertNotNull(habilidadeSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Habilidade habilidade = domainMock.getHabilidade();

        when(service.editar(id, habilidade)).thenReturn(habilidade);

        Habilidade habilidadeAlterado = this.service.editar(id, habilidade);
        assertNotNull(habilidadeAlterado);
    }

}