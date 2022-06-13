package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Cidade;
import br.com.stagiun.tccstagiun.model.domain.Dica;
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
public class DicaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DicaRepository dicaRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private Dica getDica() {
        return Dica.builder()
                .titulo("Como virar um ninja em Java")
                .descricao("Aprenda o que estudar para se tornar um ninja")
                .linksUteis("links")
                .dataPublicacao("25/05/2022")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_dica_if_repository_is_empty() {
        Iterable<Dica> seeds = dicaRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_dica() {
        Dica dica = dicaRepository.save(getDica());

        assertThat(dica).hasFieldOrPropertyWithValue("titulo", "Como virar um ninja em Java");
        assertThat(dica).hasFieldOrPropertyWithValue("descricao", "Aprenda o que estudar para se tornar um ninja");
        assertThat(dica).hasFieldOrPropertyWithValue("links_uteis", "links");
        assertThat(dica).hasFieldOrPropertyWithValue("data_publicacao", "25/05/2022");

    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_dica() {
        Dica dica = getDica();
        entityManager.persist(dica);

        Optional<Dica> found = dicaRepository.findById(dica.getId());
        assertThat(found.get()).isEqualTo(dica);
    }

    @Test
    public void should_found_null_dica() {
        Optional<Dica> fromDb = dicaRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnDica() {
        Dica dica = getDica();
        entityManager.persistAndFlush(dica);

        Dica fromDb = dicaRepository.findById(dica.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(dica);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Dica fromDb = dicaRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Dica dica = getDica();
        Dica dica1 = getDica();
        Dica dica2 = getDica();

        entityManager.persist(dica);
        entityManager.persist(dica1);
        entityManager.persist(dica2);
        entityManager.flush();

        Iterator<Dica> allCountries = dicaRepository.findAll().iterator();
        List<Dica> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("titulo").contains("Como virar um ninja em Java");
        assertThat(countries).extracting("descricao").contains("Aprenda o que estudar para se tornar um ninja");
        assertThat(countries).extracting("links_uteis").contains("links");
        assertThat(countries).extracting("data_publicacao").contains("25/05/2022");
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Dica dica) throws AssertionError {
        Optional<ConstraintViolation<Dica>> violation = validator.validate(dica).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
