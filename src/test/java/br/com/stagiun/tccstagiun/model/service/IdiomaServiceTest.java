package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Idioma;
import br.com.stagiun.tccstagiun.model.repository.IdiomaRepository;
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
class IdiomaServiceTest {

    @Mock
    IdiomaRepository idiomaRepository;

    @MockBean
    IdiomaService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_idiomaes() {
        List<Idioma> idiomaList = new ArrayList<>();

        Idioma idioma = domainMock.getIdioma();
        idiomaList.add(idioma);

        when(service.list()).thenReturn(idiomaList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Idioma idioma = domainMock.getIdioma();
        when(service.findById(id)).thenReturn(Optional.of(idioma));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Idioma idioma = domainMock.getIdioma();

        when(this.service.salvar(idioma)).thenReturn(idioma);

        Idioma idiomaSalvo = this.service.salvar(idioma);
        assertNotNull(idiomaSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Idioma idioma = domainMock.getIdioma();

        when(service.editar(id, idioma)).thenReturn(idioma);

        Idioma idiomaAlterado = this.service.editar(id, idioma);
        assertNotNull(idiomaAlterado);
    }

}