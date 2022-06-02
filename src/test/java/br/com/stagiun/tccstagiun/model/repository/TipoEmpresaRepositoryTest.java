package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.TipoEmpresa;
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
public class TipoEmpresaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TipoEmpresaRepository tipoEmpresaRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private TipoEmpresa getTipoEmpresa() {
        return TipoEmpresa.builder()
                .descricao("Tecnologia")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_tipoEmpresa_if_repository_is_empty() {
        Iterable<TipoEmpresa> seeds = tipoEmpresaRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_tipoEmpresa() {
        TipoEmpresa tipoEmpresa = tipoEmpresaRepository.save(getTipoEmpresa());

        assertThat(tipoEmpresa).hasFieldOrPropertyWithValue("descricao", "Tecnologia");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_tipoEmpresa() {
        TipoEmpresa tipoEmpresa = getTipoEmpresa();
        entityManager.persist(tipoEmpresa);

        Optional<TipoEmpresa> found = tipoEmpresaRepository.findById(tipoEmpresa.getId());
        assertThat(found.get()).isEqualTo(tipoEmpresa);
    }

    @Test
    public void should_found_null_tipoEmpresa() {
        Optional<TipoEmpresa> fromDb = tipoEmpresaRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnTipoEmpresa() {
        TipoEmpresa tipoEmpresa = getTipoEmpresa();
        entityManager.persistAndFlush(tipoEmpresa);

        TipoEmpresa fromDb = tipoEmpresaRepository.findById(tipoEmpresa.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(tipoEmpresa);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        TipoEmpresa fromDb = tipoEmpresaRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        TipoEmpresa tipoEmpresa = getTipoEmpresa();
        TipoEmpresa tipoEmpresa1 = getTipoEmpresa();
        TipoEmpresa tipoEmpresa2 = getTipoEmpresa();

        entityManager.persist(tipoEmpresa);
        entityManager.persist(tipoEmpresa1);
        entityManager.persist(tipoEmpresa2);
        entityManager.flush();

        Iterator<TipoEmpresa> allCountries = tipoEmpresaRepository.findAll().iterator();
        List<TipoEmpresa> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("Tecnologia");
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(TipoEmpresa tipoEmpresa) throws AssertionError {
        Optional<ConstraintViolation<TipoEmpresa>> violation = validator.validate(tipoEmpresa).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
