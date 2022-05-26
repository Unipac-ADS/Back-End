package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Turma;
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
public class TurmaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TurmaRepository turmaRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private Turma getTurma() {
        return Turma.builder()
                .descricao("ADS")
                .periodo(5)
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_turma_if_repository_is_empty() {
        Iterable<Turma> seeds = turmaRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_turma() {
        Turma turma = turmaRepository.save(getTurma());

        assertThat(turma).hasFieldOrPropertyWithValue("descricao", "ADS");
        assertThat(turma).hasFieldOrPropertyWithValue("periodo", 5);
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_turma() {
        Turma turma = getTurma();
        entityManager.persist(turma);

        Optional<Turma> found = turmaRepository.findById(turma.getId());
        assertThat(found.get()).isEqualTo(turma);
    }

    @Test
    public void should_found_null_turma() {
        Optional<Turma> fromDb = turmaRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnTurma() {
        Turma turma = getTurma();
        entityManager.persistAndFlush(turma);

        Turma fromDb = turmaRepository.findById(turma.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(turma);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Turma fromDb = turmaRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Turma turma = getTurma();
        Turma turma1 = getTurma();
        Turma turma2 = getTurma();

        entityManager.persist(turma);
        entityManager.persist(turma1);
        entityManager.persist(turma2);
        entityManager.flush();

        Iterator<Turma> allCountries = turmaRepository.findAll().iterator();
        List<Turma> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("ADS");
        assertThat(countries).extracting("periodo").contains(5);
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Turma turma) throws AssertionError {
        Optional<ConstraintViolation<Turma>> violation = validator.validate(turma).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
