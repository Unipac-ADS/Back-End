package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
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
@ContextConfiguration(classes = {TccApplication.class})
//@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@ActiveProfiles(value = "local")
//@TestPropertySource(locations = "classpath:test.properties")
//@Profile("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FaculdadeRepositoryTest {

    @Autowired
    private FaculdadeRepository faculdadeRepository;

    private Faculdade getFaculdade() {
        return Faculdade.builder()
                .nome("Unipac")
                .build();
    }

    @Test
    public void should_find_no_faculdade_if_repository_is_empty() {
        Iterable<Faculdade> seeds = faculdadeRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_faculdade() {
        Faculdade faculdade = faculdadeRepository.save(getFaculdade());

        assertThat(faculdade).hasFieldOrPropertyWithValue("nome", "Unipac");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_faculdade() {
        Faculdade faculdade = getFaculdade();
        faculdadeRepository.save(faculdade);

        Optional<Faculdade> found = faculdadeRepository.findById(faculdade.getId());
        assertThat(found.get()).isEqualTo(faculdade);
    }

    @Test
    public void should_found_null_faculdade() {
        Optional<Faculdade> fromDb = faculdadeRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnFaculdade() {
        Faculdade faculdade = getFaculdade();
        faculdadeRepository.save(faculdade);

        Faculdade fromDb = faculdadeRepository.findById(faculdade.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(faculdade);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Faculdade fromDb = faculdadeRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Faculdade faculdade = getFaculdade();
        Faculdade faculdade1 = getFaculdade();
        Faculdade faculdade2 = getFaculdade();

        faculdadeRepository.save(faculdade);
        faculdadeRepository.save(faculdade1);
        faculdadeRepository.save(faculdade2);

        Iterator<Faculdade> allCountries = faculdadeRepository.findAll().iterator();
        List<Faculdade> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("nome").contains("Unipac");
    }

}
