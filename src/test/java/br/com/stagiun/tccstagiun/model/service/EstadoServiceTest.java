package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Estado;
import br.com.stagiun.tccstagiun.model.repository.EstadoRepository;
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
class EstadoServiceTest {

    @Mock
    EstadoRepository estadoRepository;

    @MockBean
    EstadoService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_estadoes() {
        List<Estado> estadoList = new ArrayList<>();

        Estado estado = domainMock.getEstado();
        estadoList.add(estado);

        Estado estado2 = domainMock.getEstado2();
        estadoList.add(estado2);

        Estado estado3 = domainMock.getEstado3();
        estadoList.add(estado3);

        when(service.list()).thenReturn(estadoList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Estado estado = domainMock.getEstado();
        when(service.findById(id)).thenReturn(Optional.of(estado));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Estado estado = domainMock.getEstado();

        when(this.service.salvar(estado)).thenReturn(estado);

        Estado estadoSalvo = this.service.salvar(estado);
        assertNotNull(estadoSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Estado estado = domainMock.getEstado();

        when(service.editar(id, estado)).thenReturn(estado);

        Estado estadoAlterado = this.service.editar(id, estado);
        assertNotNull(estadoAlterado);
    }

}