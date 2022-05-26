package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Habilidade;
import br.com.stagiun.tccstagiun.model.domain.Idioma;
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
public class IdiomaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IdiomaRepository idiomaRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private Idioma getIdioma() {
        return Idioma.builder()
                .nome("Inglês")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_idioma_if_repository_is_empty() {
        Iterable<Idioma> seeds = idiomaRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_idioma() {
        Idioma idioma = idiomaRepository.save(getIdioma());

        assertThat(idioma).hasFieldOrPropertyWithValue("nome", "Inglês");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_idioma() {
        Idioma idioma = getIdioma();
        entityManager.persist(idioma);

        Optional<Idioma> found = idiomaRepository.findById(idioma.getId());
        assertThat(found.get()).isEqualTo(idioma);
    }

    @Test
    public void should_found_null_idioma() {
        Optional<Idioma> fromDb = idiomaRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnIdioma() {
        Idioma idioma = getIdioma();
        entityManager.persistAndFlush(idioma);

        Idioma fromDb = idiomaRepository.findById(idioma.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(idioma);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Idioma fromDb = idiomaRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Idioma idioma = getIdioma();
        Idioma idioma1 = getIdioma();
        Idioma idioma2 = getIdioma();

        entityManager.persist(idioma);
        entityManager.persist(idioma1);
        entityManager.persist(idioma2);
        entityManager.flush();

        Iterator<Idioma> allCountries = idiomaRepository.findAll().iterator();
        List<Idioma> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("nome").contains("Inglês");
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Idioma idioma) throws AssertionError {
        Optional<ConstraintViolation<Idioma>> violation = validator.validate(idioma).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
