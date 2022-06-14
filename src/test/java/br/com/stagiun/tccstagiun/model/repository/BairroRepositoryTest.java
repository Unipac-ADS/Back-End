package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;
import br.com.stagiun.tccstagiun.model.domain.Bairro;
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
public class BairroRepositoryTest {

    @Autowired
    private BairroRepository bairroRepository;

    private Bairro getBairro() {
        return Bairro.builder()
                .descricao("AP")
                .build();
    }

    @Test
    public void should_find_no_bairro_if_repository_is_empty() {
        Iterable<Bairro> seeds = bairroRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_bairro() {
        Bairro bairro = bairroRepository.save(getBairro());

        assertThat(bairro).hasFieldOrPropertyWithValue("descricao","AP");

    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
      //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_bairro() {
        Bairro bairro = getBairro();
        bairroRepository.save(bairro);

        Optional<Bairro> found = bairroRepository.findById(bairro.getId());
        assertThat(found.get()).isEqualTo(bairro);
    }

    @Test
    public void should_found_null_bairro() {
        Optional<Bairro> fromDb = bairroRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnBairro() {
        Bairro bairro = getBairro();
        bairroRepository.save(bairro);

        Bairro fromDb = bairroRepository.findById(bairro.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(bairro);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Bairro fromDb = bairroRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Bairro bairro = getBairro();
        Bairro bairro1 = getBairro();
        Bairro bairro2 = getBairro();

        bairroRepository.save(bairro);
        bairroRepository.save(bairro1);
        bairroRepository.save(bairro2);

        Iterator<Bairro> allCountries = bairroRepository.findAll().iterator();
        List<Bairro> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("AP");
    }
}
