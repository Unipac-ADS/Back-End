package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.Pais;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
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
public class PaisRepositoryTest {

    @Autowired
    private PaisRepository paisRepository;

    private Pais getPais() {
        return Pais.builder()
                .descricao("Brasil")
                .build();
    }

    @Test
    public void should_find_no_pais_if_repository_is_empty() {
        Iterable<Pais> seeds = paisRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_pais() {
        Pais pais = paisRepository.save(getPais());

        assertThat(pais).hasFieldOrPropertyWithValue("descricao", "Brasil");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_pais() {
        Pais pais = getPais();
        paisRepository.save(pais);

        Optional<Pais> found = paisRepository.findById(pais.getId());
        assertThat(found.get()).isEqualTo(pais);
    }

    @Test
    public void should_found_null_pais() {
        Optional<Pais> fromDb = paisRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnPais() {
        Pais pais = getPais();
        paisRepository.save(pais);

        Pais fromDb = paisRepository.findById(pais.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(pais);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Pais fromDb = paisRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Pais pais = getPais();
        Pais pais1 = getPais();
        Pais pais2 = getPais();

        paisRepository.save(pais);
        paisRepository.save(pais1);
        paisRepository.save(pais2);

        Iterator<Pais> allCountries = paisRepository.findAll().iterator();
        List<Pais> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("Brasil");
    }

}
