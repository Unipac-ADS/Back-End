package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Empresa;
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
public class EmpresaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmpresaRepository empresaRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private Empresa getEmpresa() {
        return Empresa.builder()
                .nome_fantasia("Stagiun")
                .razao_social("ADS-Stagiun")
                .cnpj(22244455)
                .telefone(32324545)
                .email("stagiun@gmail.com")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_empresa_if_repository_is_empty() {
        Iterable<Empresa> seeds = empresaRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_empresa() {
        Empresa empresa = empresaRepository.save(getEmpresa());

        assertThat(empresa).hasFieldOrPropertyWithValue("nome_fantasia", "Stagiun");
        assertThat(empresa).hasFieldOrPropertyWithValue("razao_social", "ADS-Stagiun");
        assertThat(empresa).hasFieldOrPropertyWithValue("cnpj", 22244455);
        assertThat(empresa).hasFieldOrPropertyWithValue("telefone", 32324545);
        assertThat(empresa).hasFieldOrPropertyWithValue("email", "stagiun@gmail.com");

    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_empresa() {
        Empresa empresa = getEmpresa();
        entityManager.persist(empresa);

        Optional<Empresa> found = empresaRepository.findById(empresa.getId());
        assertThat(found.get()).isEqualTo(empresa);
    }

    @Test
    public void should_found_null_empresa() {
        Optional<Empresa> fromDb = empresaRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnEmpresa() {
        Empresa empresa = getEmpresa();
        entityManager.persistAndFlush(empresa);

        Empresa fromDb = empresaRepository.findById(empresa.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(empresa);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Empresa fromDb = empresaRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Empresa empresa = getEmpresa();
        Empresa empresa1 = getEmpresa();
        Empresa empresa2 = getEmpresa();

        entityManager.persist(empresa);
        entityManager.persist(empresa1);
        entityManager.persist(empresa2);
        entityManager.flush();

        Iterator<Empresa> allCountries = empresaRepository.findAll().iterator();
        List<Empresa> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("nome_fantasia").contains("Stagiun");
        assertThat(countries).extracting("razao_social").contains("ADS-Stagiun");
        assertThat(countries).extracting("cnpj").contains(22244455);
        assertThat(countries).extracting("telefone").contains(32324545);
        assertThat(countries).extracting("email").contains("stagiun@gmail.com");
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Empresa empresa) throws AssertionError {
        Optional<ConstraintViolation<Empresa>> violation = validator.validate(empresa).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
