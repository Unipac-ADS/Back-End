package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Bairro;
import br.com.stagiun.tccstagiun.model.repository.BairroRepository;
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
class BairroServiceTest {

    @Mock
    BairroRepository bairroRepository;

    @MockBean
    BairroService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_bairroes() {
        List<Bairro> bairroList = new ArrayList<>();

        Bairro bairro = domainMock.getBairro();
        bairroList.add(bairro);

        Bairro bairro2 = domainMock.getBairro2();
        bairroList.add(bairro2);

        Bairro bairro3 = domainMock.getBairro3();
        bairroList.add(bairro3);

        when(service.list()).thenReturn(bairroList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Bairro bairro = domainMock.getBairro();
        when(service.findById(id)).thenReturn(Optional.of(bairro));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Bairro bairro = domainMock.getBairro();

        when(this.service.salvar(bairro)).thenReturn(bairro);

        Bairro bairroSalvo = this.service.salvar(bairro);
        assertNotNull(bairroSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Bairro bairro = domainMock.getBairro();

        when(service.editar(id, bairro)).thenReturn(bairro);

        Bairro bairroAlterado = this.service.editar(id, bairro);
        assertNotNull(bairroAlterado);
    }

}