package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.Turma;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Profile;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { TccApplication.class})
//@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@ActiveProfiles(value = "local")
//@TestPropertySource(locations = "classpath:test.properties")
//@Profile("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TurmaRepositoryTest {

    @Autowired
    private TurmaRepository turmaRepository;

    private Turma getTurma() {
        return Turma.builder()
                .descricao("ADS")
                .periodo(5)
                .build();
    }

    @Test
    public void should_find_no_turma_if_repository_is_empty() {
        Iterable<Turma> seeds = turmaRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_turma() {
        Turma turma = turmaRepository.save(getTurma());

        assertThat(turma).hasFieldOrPropertyWithValue("descricao", "ADS");
        assertThat(turma).hasFieldOrPropertyWithValue("periodo", 5);
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_turma() {
        Turma turma = getTurma();
        turmaRepository.save(turma);

        Optional<Turma> found = turmaRepository.findById(turma.getId());
        assertThat(found.get()).isEqualTo(turma);
    }

    @Test
    public void should_found_null_turma() {
        Optional<Turma> fromDb = turmaRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnTurma() {
        Turma turma = getTurma();
        turmaRepository.save(turma);

        Turma fromDb = turmaRepository.findById(turma.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(turma);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Turma fromDb = turmaRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Turma turma = getTurma();
        Turma turma1 = getTurma();
        Turma turma2 = getTurma();

        turmaRepository.save(turma);
        turmaRepository.save(turma1);
        turmaRepository.save(turma2);

        Iterator<Turma> allCountries = turmaRepository.findAll().iterator();
        List<Turma> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("ADS");
        assertThat(countries).extracting("periodo").contains(5);
    }

}
