package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Pais;
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
public class PaisRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PaisRepository paisRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private Pais getPais() {
        return Pais.builder()
                .descricao("Brasil")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_pais_if_repository_is_empty() {
        Iterable<Pais> seeds = paisRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_pais() {
        Pais pais = paisRepository.save(getPais());

        assertThat(pais).hasFieldOrPropertyWithValue("descricao", "Brasil");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_pais() {
        Pais pais = getPais();
        entityManager.persist(pais);

        Optional<Pais> found = paisRepository.findById(pais.getId());
        assertThat(found.get()).isEqualTo(pais);
    }

    @Test
    public void should_found_null_pais() {
        Optional<Pais> fromDb = paisRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnPais() {
        Pais pais = getPais();
        entityManager.persistAndFlush(pais);

        Pais fromDb = paisRepository.findById(pais.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(pais);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Pais fromDb = paisRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Pais pais = getPais();
        Pais pais1 = getPais();
        Pais pais2 = getPais();

        entityManager.persist(pais);
        entityManager.persist(pais1);
        entityManager.persist(pais2);
        entityManager.flush();

        Iterator<Pais> allCountries = paisRepository.findAll().iterator();
        List<Pais> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("Brasil");
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Pais pais) throws AssertionError {
        Optional<ConstraintViolation<Pais>> violation = validator.validate(pais).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
