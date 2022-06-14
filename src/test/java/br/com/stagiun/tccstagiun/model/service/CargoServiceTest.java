package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Cargo;
import br.com.stagiun.tccstagiun.model.repository.CargoRepository;
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
class CargoServiceTest {

    @Mock
    CargoRepository cargoRepository;

    @MockBean
    CargoService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_cargoes() {
        List<Cargo> cargoList = new ArrayList<>();

        Cargo cargo = domainMock.getCargo();
        cargoList.add(cargo);

        when(service.list()).thenReturn(cargoList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Cargo cargo = domainMock.getCargo();
        when(service.findById(id)).thenReturn(Optional.of(cargo));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Cargo cargo = domainMock.getCargo();

        when(this.service.salvar(cargo)).thenReturn(cargo);

        Cargo cargoSalvo = this.service.salvar(cargo);
        assertNotNull(cargoSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Cargo cargo = domainMock.getCargo();

        when(service.editar(id, cargo)).thenReturn(cargo);

        Cargo cargoAlterado = this.service.editar(id, cargo);
        assertNotNull(cargoAlterado);
    }

}