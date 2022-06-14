package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;
import br.com.stagiun.tccstagiun.model.domain.AplicacaoAlunoVaga;
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
public class AplicacaoAlunoVagaRepositoryTest {

    @Autowired
    private AplicacaoAlunoVagaRepository aplicacaoAlunoVagaRepository;


    private AplicacaoAlunoVaga getAplicacaoAlunoVaga() {
        return AplicacaoAlunoVaga.builder()
                .dataAplicacao("30/04/2022")
                .build();
    }

    @Test
    public void should_find_no_aplicacaoAlunoVaga_if_repository_is_empty() {
        Iterable<AplicacaoAlunoVaga> seeds = aplicacaoAlunoVagaRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Disabled
    public void should_store_a_aplicacaoAlunoVaga() {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = aplicacaoAlunoVagaRepository.save(getAplicacaoAlunoVaga());

        assertThat(aplicacaoAlunoVaga).hasFieldOrPropertyWithValue("data_aplicacao","30/04/2022");

    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
      //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_aplicacaoAlunoVaga() {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = getAplicacaoAlunoVaga();
        aplicacaoAlunoVagaRepository.save(aplicacaoAlunoVaga);

        Optional<AplicacaoAlunoVaga> found = aplicacaoAlunoVagaRepository.findById(aplicacaoAlunoVaga.getId());
        assertThat(found.get()).isEqualTo(aplicacaoAlunoVaga);
    }

    @Test
    public void should_found_null_aplicacaoAlunoVaga() {
        Optional<AplicacaoAlunoVaga> fromDb = aplicacaoAlunoVagaRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnAplicacaoAlunoVaga() {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = getAplicacaoAlunoVaga();
        aplicacaoAlunoVagaRepository.save(aplicacaoAlunoVaga);

        AplicacaoAlunoVaga fromDb = aplicacaoAlunoVagaRepository.findById(aplicacaoAlunoVaga.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(aplicacaoAlunoVaga);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        AplicacaoAlunoVaga fromDb = aplicacaoAlunoVagaRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Disabled
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = getAplicacaoAlunoVaga();
        AplicacaoAlunoVaga aplicacaoAlunoVaga1 = getAplicacaoAlunoVaga();
        AplicacaoAlunoVaga aplicacaoAlunoVaga2 = getAplicacaoAlunoVaga();

        aplicacaoAlunoVagaRepository.save(aplicacaoAlunoVaga);
        aplicacaoAlunoVagaRepository.save(aplicacaoAlunoVaga1);
        aplicacaoAlunoVagaRepository.save(aplicacaoAlunoVaga2);

        Iterator<AplicacaoAlunoVaga> allCountries = aplicacaoAlunoVagaRepository.findAll().iterator();
        List<AplicacaoAlunoVaga> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("data_aplicacao").contains("30/04/2022");

    }
}
