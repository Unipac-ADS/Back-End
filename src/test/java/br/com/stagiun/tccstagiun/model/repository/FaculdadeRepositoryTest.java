package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Faculdade;
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
public class FaculdadeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FaculdadeRepository faculdadeRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private Faculdade getFaculdade() {
        return Faculdade.builder()
                .nome("Unipac")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_faculdade_if_repository_is_empty() {
        Iterable<Faculdade> seeds = faculdadeRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_faculdade() {
        Faculdade faculdade = faculdadeRepository.save(getFaculdade());

        assertThat(faculdade).hasFieldOrPropertyWithValue("nome", "Unipac");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_faculdade() {
        Faculdade faculdade = getFaculdade();
        entityManager.persist(faculdade);

        Optional<Faculdade> found = faculdadeRepository.findById(faculdade.getId());
        assertThat(found.get()).isEqualTo(faculdade);
    }

    @Test
    public void should_found_null_faculdade() {
        Optional<Faculdade> fromDb = faculdadeRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnFaculdade() {
        Faculdade faculdade = getFaculdade();
        entityManager.persistAndFlush(faculdade);

        Faculdade fromDb = faculdadeRepository.findById(faculdade.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(faculdade);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Faculdade fromDb = faculdadeRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Faculdade faculdade = getFaculdade();
        Faculdade faculdade1 = getFaculdade();
        Faculdade faculdade2 = getFaculdade();

        entityManager.persist(faculdade);
        entityManager.persist(faculdade1);
        entityManager.persist(faculdade2);
        entityManager.flush();

        Iterator<Faculdade> allCountries = faculdadeRepository.findAll().iterator();
        List<Faculdade> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("nome").contains("Unipac");
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Faculdade faculdade) throws AssertionError {
        Optional<ConstraintViolation<Faculdade>> violation = validator.validate(faculdade).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
