package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;
import br.com.stagiun.tccstagiun.model.domain.Cargo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CargoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CargoRepository cargoRepository;

    private Validator validator;

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
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_cargo_if_repository_is_empty() {
        Iterable<Cargo> seeds = cargoRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_cargo() {
        Cargo cargo = cargoRepository.save(getCargo());

        assertThat(cargo).hasFieldOrPropertyWithValue("descricao", "Dev Front-End");
        assertThat(cargo).hasFieldOrPropertyWithValue("experiencia", "Júnior");
        assertThat(cargo).hasFieldOrPropertyWithValue("area_atuacao", "Web");
        assertThat(cargo).hasFieldOrPropertyWithValue("beneficios", "Tudo");
    //    assertThat(cargo).hasFieldOrPropertyWithValue("salario", "5.9");
        assertThat(cargo).hasFieldOrPropertyWithValue("habilidades_desejadas", "Inglês");
        assertThat(cargo).hasFieldOrPropertyWithValue("competencias_desejadas", "HTML/CSS, JavaScript, React/Angular");

    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_cargo() {
        Cargo cargo = getCargo();
        entityManager.persist(cargo);

        Optional<Cargo> found = cargoRepository.findById(cargo.getId());
        assertThat(found.get()).isEqualTo(cargo);
    }

    @Test
    public void should_found_null_cargo() {
        Optional<Cargo> fromDb = cargoRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnCargo() {
        Cargo cargo = getCargo();
        entityManager.persistAndFlush(cargo);

        Cargo fromDb = cargoRepository.findById(cargo.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(cargo);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Cargo fromDb = cargoRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Cargo cargo = getCargo();
        Cargo cargo1 = getCargo();
        Cargo cargo2 = getCargo();

        entityManager.persist(cargo);
        entityManager.persist(cargo1);
        entityManager.persist(cargo2);
        entityManager.flush();

        Iterator<Cargo> allCountries = cargoRepository.findAll().iterator();
        List<Cargo> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("Dev Front-End");
        assertThat(countries).extracting("experiencia").contains("Júnior");
        assertThat(countries).extracting("area_atuacao").contains("Web");
        assertThat(countries).extracting("beneficios").contains("Tudo");
    //    assertThat(countries).extracting("salario").contains("5.9");
        assertThat(countries).extracting("habilidades_desejadas").contains("Inglês");
        assertThat(countries).extracting("competencias_desejadas").contains("HTML/CSS, JavaScript, React/Angular");

    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Cargo cargo) throws AssertionError {
        Optional<ConstraintViolation<Cargo>> violation = validator.validate(cargo).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
