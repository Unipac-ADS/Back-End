package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Perfil;
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
public class PerfilRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PerfilRepository perfilRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private Perfil getPerfil() {
        return Perfil.builder()
                .descricao("Aluno")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_perfil_if_repository_is_empty() {
        Iterable<Perfil> seeds = perfilRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_perfil() {
        Perfil perfil = perfilRepository.save(getPerfil());

        assertThat(perfil).hasFieldOrPropertyWithValue("descricao", "Aluno");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_perfil() {
        Perfil perfil = getPerfil();
        entityManager.persist(perfil);

        Optional<Perfil> found = perfilRepository.findById(perfil.getId());
        assertThat(found.get()).isEqualTo(perfil);
    }

    @Test
    public void should_found_null_perfil() {
        Optional<Perfil> fromDb = perfilRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnPerfil() {
        Perfil perfil = getPerfil();
        entityManager.persistAndFlush(perfil);

        Perfil fromDb = perfilRepository.findById(perfil.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(perfil);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Perfil fromDb = perfilRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Perfil perfil = getPerfil();
        Perfil perfil1 = getPerfil();
        Perfil perfil2 = getPerfil();

        entityManager.persist(perfil);
        entityManager.persist(perfil1);
        entityManager.persist(perfil2);
        entityManager.flush();

        Iterator<Perfil> allCountries = perfilRepository.findAll().iterator();
        List<Perfil> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("Aluno");
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Perfil perfil) throws AssertionError {
        Optional<ConstraintViolation<Perfil>> violation = validator.validate(perfil).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
