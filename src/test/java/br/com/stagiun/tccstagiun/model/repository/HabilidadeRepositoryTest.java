package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.Faculdade;
import br.com.stagiun.tccstagiun.model.domain.Habilidade;
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
public class HabilidadeRepositoryTest {

    @Autowired
    private HabilidadeRepository habilidadeRepository;

    private Habilidade getHabilidade() {
        return Habilidade.builder()
                .nome("Proativo")
                .build();
    }

    @Test
    public void should_find_no_habilidade_if_repository_is_empty() {
        Iterable<Habilidade> seeds = habilidadeRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_habilidade() {
        Habilidade habilidade = habilidadeRepository.save(getHabilidade());

        assertThat(habilidade).hasFieldOrPropertyWithValue("nome", "Proativo");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_habilidade() {
        Habilidade habilidade = getHabilidade();
        habilidadeRepository.save(habilidade);

        Optional<Habilidade> found = habilidadeRepository.findById(habilidade.getId());
        assertThat(found.get()).isEqualTo(habilidade);
    }

    @Test
    public void should_found_null_habilidade() {
        Optional<Habilidade> fromDb = habilidadeRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnFaculdade() {
        Habilidade habilidade = getHabilidade();
        habilidadeRepository.save(habilidade);

        Habilidade fromDb = habilidadeRepository.findById(habilidade.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(habilidade);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Habilidade fromDb = habilidadeRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Habilidade habilidade = getHabilidade();
        Habilidade habilidade1 = getHabilidade();
        Habilidade habilidade2 = getHabilidade();

        habilidadeRepository.save(habilidade);
        habilidadeRepository.save(habilidade1);
        habilidadeRepository.save(habilidade2);
        Iterator<Habilidade> allCountries = habilidadeRepository.findAll().iterator();
        List<Habilidade> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("nome").contains("Proativo");
    }

}
