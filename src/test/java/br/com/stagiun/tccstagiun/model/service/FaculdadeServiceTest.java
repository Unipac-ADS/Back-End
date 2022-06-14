package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Faculdade;
import br.com.stagiun.tccstagiun.model.repository.FaculdadeRepository;
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
class FaculdadeServiceTest {

    @Mock
    FaculdadeRepository faculdadeRepository;

    @MockBean
    FaculdadeService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_faculdadees() {
        List<Faculdade> faculdadeList = new ArrayList<>();

        Faculdade faculdade = domainMock.getFaculdade();
        faculdadeList.add(faculdade);

        when(service.list()).thenReturn(faculdadeList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Faculdade faculdade = domainMock.getFaculdade();
        when(service.findById(id)).thenReturn(Optional.of(faculdade));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Faculdade faculdade = domainMock.getFaculdade();

        when(this.service.salvar(faculdade)).thenReturn(faculdade);

        Faculdade faculdadeSalvo = this.service.salvar(faculdade);
        assertNotNull(faculdadeSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Faculdade faculdade = domainMock.getFaculdade();

        when(service.editar(id, faculdade)).thenReturn(faculdade);

        Faculdade faculdadeAlterado = this.service.editar(id, faculdade);
        assertNotNull(faculdadeAlterado);
    }

}