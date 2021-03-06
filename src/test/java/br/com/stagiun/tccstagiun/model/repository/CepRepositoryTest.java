package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.Bairro;
import br.com.stagiun.tccstagiun.model.domain.Cep;
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
public class CepRepositoryTest {

    @Autowired
    private CepRepository cepRepository;

    private Cep getCep() {
        return Cep.builder()
                .descricao("48688-125")
                .build();
    }

    @Test
    public void should_find_no_cep_if_repository_is_empty() {
        Iterable<Cep> seeds = cepRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_cep() {
        Cep cep = cepRepository.save(getCep());

        assertThat(cep).hasFieldOrPropertyWithValue("descricao","48688-125");

    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
      //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_cep() {
        Cep cep = getCep();
        cepRepository.save(cep);

        Optional<Cep> found = cepRepository.findById(cep.getId());
        assertThat(found.get()).isEqualTo(cep);
    }

    @Test
    public void should_found_null_cep() {
        Optional<Cep> fromDb = cepRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnCep() {
        Cep cep = getCep();
        cepRepository.save(cep);

        Cep fromDb = cepRepository.findById(cep.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(cep);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Cep fromDb = cepRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Cep cep = getCep();
        Cep cep1 = getCep();
        Cep cep2 = getCep();

        cepRepository.save(cep);
        cepRepository.save(cep1);
        cepRepository.save(cep2);

        Iterator<Cep> allCountries = cepRepository.findAll().iterator();
        List<Cep> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("48688-125");
    }
}
