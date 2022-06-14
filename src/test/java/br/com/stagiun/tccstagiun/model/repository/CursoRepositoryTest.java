package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.Cidade;
import br.com.stagiun.tccstagiun.model.domain.Curso;
import br.com.stagiun.tccstagiun.model.domain.Faculdade;
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
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    private Curso getCurso() {
        return Curso.builder()
                .descricao("Análise e Desenvolvimento de Sistemas")
                .build();
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
        cursoRepository.save(curso);

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
        cursoRepository.save(curso);

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

        cursoRepository.save(curso);
        cursoRepository.save(curso1);
        cursoRepository.save(curso2);

        Iterator<Curso> allCountries = cursoRepository.findAll().iterator();
        List<Curso> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));


        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("Análise e Desenvolvimento de Sistemas");
    }

}
