package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.Aluno;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { TccApplication.class})
//@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@ActiveProfiles(value = "local")
//@TestPropertySource(locations = "classpath:test.properties")
//@Profile("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

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

    @Test
    public void should_find_no_alunos_if_repository_is_empty() {
        Iterable<Aluno> seeds = alunoRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Disabled
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
        alunoRepository.save(aluno);

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
        alunoRepository.save(aluno);

        Aluno fromDb = alunoRepository.findById(aluno.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(aluno);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Aluno fromDb = alunoRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Disabled
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Aluno aluno1 = getAluno();
        Aluno aluno2 = getAluno();
        Aluno aluno3 = getAluno();

        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);
        alunoRepository.save(aluno3);

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

}
