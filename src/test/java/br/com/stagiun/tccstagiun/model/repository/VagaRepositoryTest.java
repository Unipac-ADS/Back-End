package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Vaga;
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
public class VagaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VagaRepository vagaRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private Vaga getVaga() {
        return Vaga.builder()
                .amount(20D)
                .dataOfertaInicio("26/05/2022")
                .dataOfertaFim("30/05/2022")
                .nome("Vaga Desenvolvedor Java")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_vaga_if_repository_is_empty() {
        Iterable<Vaga> seeds = vagaRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_vaga() {
        Vaga vaga = vagaRepository.save(getVaga());

        assertThat(vaga).hasFieldOrPropertyWithValue("aplicado", 1);
        assertThat(vaga).hasFieldOrPropertyWithValue("amount", 20D);
        assertThat(vaga).hasFieldOrPropertyWithValue("data_oferta_inicio", "26/05/2022");
        assertThat(vaga).hasFieldOrPropertyWithValue("data_oferta_fim", "30/05/2022");
        assertThat(vaga).hasFieldOrPropertyWithValue("nome", "Vaga Desenvolvedor Java");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_vaga() {
        Vaga vaga = getVaga();
        entityManager.persist(vaga);

        Optional<Vaga> found = vagaRepository.findById(vaga.getId());
        assertThat(found.get()).isEqualTo(vaga);
    }

    @Test
    public void should_found_null_vaga() {
        Optional<Vaga> fromDb = vagaRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnVaga() {
        Vaga vaga = getVaga();
        entityManager.persistAndFlush(vaga);

        Vaga fromDb = vagaRepository.findById(vaga.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(vaga);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Vaga fromDb = vagaRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Vaga vaga = getVaga();
        Vaga vaga1 = getVaga();
        Vaga vaga2 = getVaga();

        entityManager.persist(vaga);
        entityManager.persist(vaga1);
        entityManager.persist(vaga2);
        entityManager.flush();

        Iterator<Vaga> allCountries = vagaRepository.findAll().iterator();
        List<Vaga> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("aplicado").contains(1);
        assertThat(countries).extracting("amount").contains(20D);
        assertThat(countries).extracting("data_oferta_inicio").contains("26/05/2022");
        assertThat(countries).extracting("nome").contains("Vaga Desenvolvedor Java");
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Vaga vaga) throws AssertionError {
        Optional<ConstraintViolation<Vaga>> violation = validator.validate(vaga).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
