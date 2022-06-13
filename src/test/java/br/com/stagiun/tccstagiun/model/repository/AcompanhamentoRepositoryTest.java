package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;
import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;
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
public class AcompanhamentoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private Acompanhamento getAcompanhamento() {
        return Acompanhamento.builder()
                .dataAplicacao("26/05/2022")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_acompanhamento_if_repository_is_empty() {
        Iterable<Acompanhamento> seeds = acompanhamentoRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_acompanhamento() {
        Acompanhamento acompanhamento = acompanhamentoRepository.save(getAcompanhamento());

        assertThat(acompanhamento).hasFieldOrPropertyWithValue("data_aplicacao","26/05/2022");

    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
      //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_acompanhamento() {
        Acompanhamento acompanhamento = getAcompanhamento();
        entityManager.persist(acompanhamento);

        Optional<Acompanhamento> found = acompanhamentoRepository.findById(acompanhamento.getId());
        assertThat(found.get()).isEqualTo(acompanhamento);
    }

    @Test
    public void should_found_null_acompanhamento() {
        Optional<Acompanhamento> fromDb = acompanhamentoRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnAcompanhamento() {
        Acompanhamento acompanhamento = getAcompanhamento();
        entityManager.persistAndFlush(acompanhamento);

        Acompanhamento fromDb = acompanhamentoRepository.findById(acompanhamento.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(acompanhamento);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Acompanhamento fromDb = acompanhamentoRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Acompanhamento acompanhamento = getAcompanhamento();
        Acompanhamento acompanhamento2 = getAcompanhamento();
        Acompanhamento acompanhamento3 = getAcompanhamento();

        entityManager.persist(acompanhamento);
        entityManager.persist(acompanhamento2);
        entityManager.persist(acompanhamento3);
        entityManager.flush();

        Iterator<Acompanhamento> allCountries = acompanhamentoRepository.findAll().iterator();
        List<Acompanhamento> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("data_aplicacao").contains("26/05/2022");

    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Acompanhamento acompanhamento) throws AssertionError {
        Optional<ConstraintViolation<Acompanhamento>> violation = validator.validate(acompanhamento).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
