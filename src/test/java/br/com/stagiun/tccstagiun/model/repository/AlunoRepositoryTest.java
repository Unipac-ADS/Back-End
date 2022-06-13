package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Aluno;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlunoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AlunoRepository alunoRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private Aluno getAluno() {
        return Aluno.builder()
                .nome("Brasil")
                .matricula("ADS001")
                .cpf(1245683215L)
                .telefone(32564748L)
                .email("brteste@hotmail.com")
                .curriculo("cvteste1")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_alunos_if_repository_is_empty() {
        Iterable<Aluno> seeds = alunoRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_aluno() {
        Aluno aluno = alunoRepository.save(getAluno());

        assertThat(aluno).hasFieldOrPropertyWithValue("nome","Brasil");
        assertThat(aluno).hasFieldOrPropertyWithValue("matricula","ADS001");
        assertThat(aluno).hasFieldOrPropertyWithValue("cpf",1245683215);
        assertThat(aluno).hasFieldOrPropertyWithValue("telefone",32564748);
        assertThat(aluno).hasFieldOrPropertyWithValue("email","brteste@hotmail.com");
        assertThat(aluno).hasFieldOrPropertyWithValue("curriculo","cvteste1");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
      //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_aluno() {
        Aluno aluno = getAluno();
        entityManager.persist(aluno);

        Optional<Aluno> found = alunoRepository.findById(aluno.getId());
        assertThat(found.get()).isEqualTo(aluno);
    }

    @Test
    public void should_found_null_aluno() {
        Optional<Aluno> fromDb = alunoRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnAluno() {
        Aluno aluno = getAluno();
        entityManager.persistAndFlush(aluno);

        Aluno fromDb = alunoRepository.findById(aluno.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(aluno);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Aluno fromDb = alunoRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Aluno aluno1 = getAluno();
        Aluno aluno2 = getAluno();
        Aluno aluno3 = getAluno();

        entityManager.persist(aluno1);
        entityManager.persist(aluno2);
        entityManager.persist(aluno3);
        entityManager.flush();

        Iterator<Aluno> allCountries = alunoRepository.findAll().iterator();
        List<Aluno> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("nome").contains("Brasil");
        assertThat(countries).extracting("matricula").contains("ADS001");
        assertThat(countries).extracting("cpf").contains(1245683215);
        assertThat(countries).extracting("telefone").contains(32564748);
        assertThat(countries).extracting("email").contains("brteste@hotmail.com");
        assertThat(countries).extracting("curriculo").contains("cvteste1");
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Aluno aluno) throws AssertionError {
        Optional<ConstraintViolation<Aluno>> violation = validator.validate(aluno).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
