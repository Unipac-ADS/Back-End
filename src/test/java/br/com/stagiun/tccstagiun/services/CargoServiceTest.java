package br.com.stagiun.tccstagiun.services;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Bairro;
import br.com.stagiun.tccstagiun.model.domain.Cargo;
import br.com.stagiun.tccstagiun.model.repository.CargoRepository;
import br.com.stagiun.tccstagiun.model.service.impl.CargoServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CargoServiceTest {

    @InjectMocks
    CargoServiceImpl cargoService;

    @Mock
    CargoRepository cargoRepository;

    //public ExpectedException thrown = ExpectedException.none();

    private Cargo getCargo() {
        return Cargo.builder()
                .descricao("Dev Front-End")
                .experiencia("Júnior")
                .areaAtuacao("Web")
                .beneficios("Tudo")
                //        .salario(new BigDecimal("5.9"))
                .habilidadesDesejadas("Inglês")
                .competenciasDesejadas("HTML/CSS, JavaScript, React/Angular")
                .build();
    }

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Cargo> list = new ArrayList<>();

        Cargo cargo = getCargo();
        Cargo cargo1 = getCargo();
        Cargo cargo2 = getCargo();

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
        when(cargoRepository.findById(1L)).thenReturn(Optional.of(getCargo()));

        Optional<Cargo> cargo = cargoService.findById(1L);

        assertEquals("Dev Front-End", cargo.get().getDescricao());
        assertEquals("Júnior", cargo.get().getExperiencia());
        assertEquals("Web", cargo.get().getAreaAtuacao());
        assertEquals("Tudo", cargo.get().getBeneficios());
        //    assertEquals("5.9", cargo.get().getSalario());
        assertEquals("Inglês", cargo.get().getHabilidadesDesejadas());
        assertEquals("HTML/CSS, JavaScript, React/Angular", cargo.get().getCompetenciasDesejadas());
    }

    @Test
    public void getFindCargoByShortIdTest() {
        Cargo cargo = getCargo();
        when(cargoService.findById(1L)).thenReturn(Optional.ofNullable(cargo));

        Optional<Cargo> result = cargoService.findById(1L);

        assertEquals("Dev Front-End", cargo.getDescricao());
        assertEquals("Júnior", cargo.getExperiencia());
        assertEquals("Web", cargo.getAreaAtuacao());
        assertEquals("Tudo", cargo.getBeneficios());
        //    assertEquals("5.9", cargo.getSalario());
        assertEquals("Inglês", cargo.getHabilidadesDesejadas());
        assertEquals("HTML/CSS, JavaScript, React/Angular", cargo.getCompetenciasDesejadas());
    }

    @Test
    public void createCargoTest() throws ResourceFoundException {
        Cargo url = getCargo();
        cargoService.salvar(url);

        verify(cargoRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreCargoTest() throws ResourceFoundException {
        Cargo cargo = getCargo();
        cargoService.salvar(cargo);

        when(cargoService.salvar(cargo)).thenReturn(cargo);
        Cargo result = cargoService.salvar(cargo);

        assertEquals("Dev Front-End", result.getDescricao());
        assertEquals("Júnior", result.getExperiencia());
        assertEquals("Web", result.getAreaAtuacao());
        assertEquals("Tudo", result.getBeneficios());
        //    assertEquals("5.9", result.getSalario());
        assertEquals("Inglês", result.getHabilidadesDesejadas());
        assertEquals("HTML/CSS, JavaScript, React/Angular", result.getCompetenciasDesejadas());
    }
}
