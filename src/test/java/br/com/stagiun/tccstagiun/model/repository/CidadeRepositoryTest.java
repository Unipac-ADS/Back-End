package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Cep;
import br.com.stagiun.tccstagiun.model.domain.Cidade;
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
public class CidadeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CidadeRepository cidadeRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private Cidade getCidade() {
        return Cidade.builder()
                .descricao("Uberlândia")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_cidade_if_repository_is_empty() {
        Iterable<Cidade> seeds = cidadeRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_cidade() {
        Cidade cidade = cidadeRepository.save(getCidade());

        assertThat(cidade).hasFieldOrPropertyWithValue("descricao","Uberlândia");

    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
      //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_cidade() {
        Cidade cidade = getCidade();
        entityManager.persist(cidade);

        Optional<Cidade> found = cidadeRepository.findById(cidade.getId());
        assertThat(found.get()).isEqualTo(cidade);
    }

    @Test
    public void should_found_null_cidade() {
        Optional<Cidade> fromDb = cidadeRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnCidade() {
        Cidade cidade = getCidade();
        entityManager.persistAndFlush(cidade);

        Cidade fromDb = cidadeRepository.findById(cidade.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(cidade);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Cidade fromDb = cidadeRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Cidade cidade = getCidade();
        Cidade cidade1 = getCidade();
        Cidade cidade2 = getCidade();

        entityManager.persist(cidade);
        entityManager.persist(cidade1);
        entityManager.persist(cidade2);
        entityManager.flush();

        Iterator<Cidade> allCountries = cidadeRepository.findAll().iterator();
        List<Cidade> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("Uberlândia");
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Cidade cidade) throws AssertionError {
        Optional<ConstraintViolation<Cidade>> violation = validator.validate(cidade).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
