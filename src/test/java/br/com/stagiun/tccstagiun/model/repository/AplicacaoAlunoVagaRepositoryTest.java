package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;
import br.com.stagiun.tccstagiun.model.domain.AplicacaoAlunoVaga;
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
public class AplicacaoAlunoVagaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AplicacaoAlunoVagaRepository aplicacaoAlunoVagaRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private AplicacaoAlunoVaga getAplicacaoAlunoVaga() {
        return AplicacaoAlunoVaga.builder()
                .data_aplicacao("30/04/2022")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_aplicacaoAlunoVaga_if_repository_is_empty() {
        Iterable<AplicacaoAlunoVaga> seeds = aplicacaoAlunoVagaRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_aplicacaoAlunoVaga() {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = aplicacaoAlunoVagaRepository.save(getAplicacaoAlunoVaga());

        assertThat(aplicacaoAlunoVaga).hasFieldOrPropertyWithValue("data_aplicacao","30/04/2022");

    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
      //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_aplicacaoAlunoVaga() {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = getAplicacaoAlunoVaga();
        entityManager.persist(aplicacaoAlunoVaga);

        Optional<AplicacaoAlunoVaga> found = aplicacaoAlunoVagaRepository.findById(aplicacaoAlunoVaga.getId());
        assertThat(found.get()).isEqualTo(aplicacaoAlunoVaga);
    }

    @Test
    public void should_found_null_aplicacaoAlunoVaga() {
        Optional<AplicacaoAlunoVaga> fromDb = aplicacaoAlunoVagaRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnAplicacaoAlunoVaga() {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = getAplicacaoAlunoVaga();
        entityManager.persistAndFlush(aplicacaoAlunoVaga);

        AplicacaoAlunoVaga fromDb = aplicacaoAlunoVagaRepository.findById(aplicacaoAlunoVaga.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(aplicacaoAlunoVaga);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        AplicacaoAlunoVaga fromDb = aplicacaoAlunoVagaRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = getAplicacaoAlunoVaga();
        AplicacaoAlunoVaga aplicacaoAlunoVaga1 = getAplicacaoAlunoVaga();
        AplicacaoAlunoVaga aplicacaoAlunoVaga2 = getAplicacaoAlunoVaga();

        entityManager.persist(aplicacaoAlunoVaga);
        entityManager.persist(aplicacaoAlunoVaga1);
        entityManager.persist(aplicacaoAlunoVaga2);
        entityManager.flush();

        Iterator<AplicacaoAlunoVaga> allCountries = aplicacaoAlunoVagaRepository.findAll().iterator();
        List<AplicacaoAlunoVaga> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("data_aplicacao").contains("30/04/2022");

    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(AplicacaoAlunoVaga aplicacaoAlunoVaga) throws AssertionError {
        Optional<ConstraintViolation<AplicacaoAlunoVaga>> violation = validator.validate(aplicacaoAlunoVaga).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
