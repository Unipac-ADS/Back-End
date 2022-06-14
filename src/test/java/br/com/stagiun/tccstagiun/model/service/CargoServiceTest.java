package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Cargo;
import br.com.stagiun.tccstagiun.model.repository.CargoRepository;
import br.com.stagiun.tccstagiun.model.service.impl.CargoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class CargoServiceTest {

    @InjectMocks
    CargoServiceImpl cargoService;

    @Mock
    CargoRepository cargoRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

       @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Cargo> list = new ArrayList<>();

        Cargo cargo = domainMock.getCargo();
        Cargo cargo1 = domainMock.getCargo();
        Cargo cargo2 = domainMock.getCargo();

        list.add(cargo);
        list.add(cargo1);
        list.add(cargo2);

        when(cargoRepository.findAll()).thenReturn(list);

        // test
        List<Cargo> urls = cargoService.list();

        assertEquals(3, urls.size());
        verify(cargoRepository, times(1)).findAll();
    }

    @Test
    public void getCargoByIdTest() {
        when(cargoRepository.findById(1L)).thenReturn(Optional.of(domainMock.getCargo()));

        Optional<Cargo> cargo = cargoService.findById(1L);

        assertEquals("Dev Front-End", cargo.get().getDescricao());
        assertEquals("Júnior", cargo.get().getExperiencia());
        assertEquals("Web", cargo.get().getAreaAtuacao());
        assertEquals("Tudo", cargo.get().getBeneficios());
        assertEquals(BigDecimal.valueOf(2999.00).setScale(2), cargo.get().getSalario());
        assertEquals("Inglês", cargo.get().getHabilidadesDesejadas());
        assertEquals("HTML/CSS, JavaScript, React/Angular", cargo.get().getCompetenciasDesejadas());
    }

    //return Cargo.builder().id(1L).descricao("Dev Front-End").areaAtuacao("Web").beneficios("Tudo").competenciasDesejadas("Inglês").experiencia("Júnior").salario(BigDecimal.valueOf(2999.00)).build();

    @Test
    public void getFindCargoByShortIdTest() {
        Cargo cargo = domainMock.getCargo();
        when(cargoService.findById(1L)).thenReturn(Optional.ofNullable(cargo));

        Optional<Cargo> result = cargoService.findById(1L);

        assertEquals("Dev Front-End", result.get().getDescricao());
        assertEquals("Júnior", result.get().getExperiencia());
        assertEquals("Web", result.get().getAreaAtuacao());
        assertEquals("Tudo", result.get().getBeneficios());
        assertEquals(BigDecimal.valueOf(2999.00).setScale(2), result.get().getSalario());
        assertEquals("Inglês", result.get().getHabilidadesDesejadas());
        assertEquals("HTML/CSS, JavaScript, React/Angular", result.get().getCompetenciasDesejadas());
    }

    @Test
    public void createCargoTest() throws ResourceFoundException {
        Cargo cargo = domainMock.getCargo();
        cargoService.salvar(cargo);

        verify(cargoRepository, times(1)).save(cargo);
    }

    @Test
    public void createAndStoreCargoTest() throws ResourceFoundException {
        Cargo cargo = domainMock.getCargo();
        cargoService.salvar(cargo);

        when(cargoService.salvar(cargo)).thenReturn(cargo);
        Cargo result = cargoService.salvar(cargo);

        assertEquals("Dev Front-End", result.getDescricao());
        assertEquals("Júnior", result.getExperiencia());
        assertEquals("Web", result.getAreaAtuacao());
        assertEquals("Tudo", result.getBeneficios());
        assertEquals(BigDecimal.valueOf(2999.00).setScale(2), result.getSalario());
        assertEquals("Inglês", result.getHabilidadesDesejadas());
        assertEquals("HTML/CSS, JavaScript, React/Angular", result.getCompetenciasDesejadas());
    }
}
