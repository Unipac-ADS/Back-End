package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Cidade;
import br.com.stagiun.tccstagiun.model.domain.Curso;
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
public class CursoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CursoRepository cursoRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private Curso getCurso() {
        return Curso.builder()
                .descricao("Análise e Desenvolvimento de Sistemas")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_curso_if_repository_is_empty() {
        Iterable<Curso> seeds = cursoRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_curso() {
        Curso curso = cursoRepository.save(getCurso());

        assertThat(curso).hasFieldOrPropertyWithValue("descricao", "Análise e Desenvolvimento de Sistemas");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_curso() {
        Curso curso = getCurso();
        entityManager.persist(curso);

        Optional<Curso> found = cursoRepository.findById(curso.getId());
        assertThat(found.get()).isEqualTo(curso);
    }

    @Test
    public void should_found_null_curso() {
        Optional<Curso> fromDb = cursoRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnCurso() {
        Curso curso = getCurso();
        entityManager.persistAndFlush(curso);

        Curso fromDb = cursoRepository.findById(curso.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(curso);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Curso fromDb = cursoRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Curso curso = getCurso();
        Curso curso1 = getCurso();
        Curso curso2 = getCurso();

        entityManager.persist(curso);
        entityManager.persist(curso1);
        entityManager.persist(curso2);
        entityManager.flush();

        Iterator<Curso> allCountries = cursoRepository.findAll().iterator();
        List<Curso> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));


        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("Análise e Desenvolvimento de Sistemas");
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Curso curso) throws AssertionError {
        Optional<ConstraintViolation<Curso>> violation = validator.validate(curso).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
