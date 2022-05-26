package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;
import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhes;
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
public class AlunoDetalhesRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AlunoDetalhesRepository alunoDetalhesRepository;

    private Validator validator;

    //public ExpectedException thrown = ExpectedException.none();

    private AlunoDetalhes getAlunoDetalhes() {
        return AlunoDetalhes.builder()
                .ano_de_inicio_curso("07/02/2020")
                .ano_de_conclusao_curso("17/07/2022")
                .experiencia("Dev Full Stack")
                .info_adicionais("Full Stack")
                .deficiencia(0)
                .sobre("Full Stack")
                .linkedin("linkedin.com/in/thor")
                .github("github/thor")
                .instagram("Thor Filho de Odin")
                .twitter("Thor Filho de Odin")
                .file_curriculo("CvThor")
                .build();
    }

    @BeforeAll
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_find_no_alunoDetalhes_if_repository_is_empty() {
        Iterable<AlunoDetalhes> seeds = alunoDetalhesRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_acompanhamento() {
        AlunoDetalhes alunoDetalhes = alunoDetalhesRepository.save(getAlunoDetalhes());

        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue( "ano_de_inicio_curso","07/02/2020");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue( "ano_de_conclusao_curso","17/07/2022");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue( "experiencia","Dev Full Stack");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue( "info_adicionais","Full Stack");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue( "deficiencia",0);
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue( "sobre","Full Stack");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue( "linkedin","linkedin.com/in/thor");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue( "github","github/thor");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue( "instagram","Thor Filho de Odin");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue( "twitter","Thor Filho de Odin");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue( "file_curriculo","CvThor");

    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
      //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_alunoDetalhes() {
        AlunoDetalhes alunoDetalhes = getAlunoDetalhes();
        entityManager.persist(alunoDetalhes);

        Optional<AlunoDetalhes> found = alunoDetalhesRepository.findById(alunoDetalhes.getId());
        assertThat(found.get()).isEqualTo(alunoDetalhes);
    }

    @Test
    public void should_found_null_alunoDetalhes() {
        Optional<AlunoDetalhes> fromDb = alunoDetalhesRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnAlunoDetalhes() {
        AlunoDetalhes alunoDetalhes = getAlunoDetalhes();
        entityManager.persistAndFlush(alunoDetalhes);

        AlunoDetalhes fromDb = alunoDetalhesRepository.findById(alunoDetalhes.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(alunoDetalhes);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        AlunoDetalhes fromDb = alunoDetalhesRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        AlunoDetalhes alunoDetalhes = getAlunoDetalhes();
        AlunoDetalhes alunoDetalhes2 = getAlunoDetalhes();
        AlunoDetalhes alunoDetalhes3 = getAlunoDetalhes();

        entityManager.persist(alunoDetalhes);
        entityManager.persist(alunoDetalhes2);
        entityManager.persist(alunoDetalhes3);
        entityManager.flush();

        Iterator<AlunoDetalhes> allCountries = alunoDetalhesRepository.findAll().iterator();
        List<AlunoDetalhes> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("ano_de_inicio_curso").contains("07/02/2020");
        assertThat(countries).extracting("ano_de_conclusao_curso").contains("17/07/2022");
        assertThat(countries).extracting("experiencia").contains("Dev Full Stack");
        assertThat(countries).extracting("info_adicionais").contains("Full Stack");
        assertThat(countries).extracting("deficiencia").contains(0);
        assertThat(countries).extracting("sobre").contains("Full Stack");
        assertThat(countries).extracting("linkedin").contains("linkedin.com/in/thor");
        assertThat(countries).extracting("github").contains("github/thor");
        assertThat(countries).extracting("instagram").contains("Thor Filho de Odin");
        assertThat(countries).extracting("twitter").contains("Thor Filho de Odin");
        assertThat(countries).extracting("file_curriculo").contains("CvThor");

    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(AlunoDetalhes alunoDetalhes) throws AssertionError {
        Optional<ConstraintViolation<AlunoDetalhes>> violation = validator.validate(alunoDetalhes).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
