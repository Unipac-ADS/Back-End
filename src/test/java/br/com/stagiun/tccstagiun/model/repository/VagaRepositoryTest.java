package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.Vaga;
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
public class VagaRepositoryTest {

    @Autowired
    private VagaRepository vagaRepository;

    private Vaga getVaga() {
        return Vaga.builder()
                .amount(20D)
                .dataOfertaInicio("26/05/2022")
                .dataOfertaFim("30/05/2022")
                .nome("Vaga Desenvolvedor Java")
                .build();
    }

    @Test
    public void should_find_no_vaga_if_repository_is_empty() {
        Iterable<Vaga> seeds = vagaRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Disabled
    public void should_store_a_vaga() {
        Vaga vaga = vagaRepository.save(getVaga());

        assertThat(vaga).hasFieldOrPropertyWithValue("aplicado", 1);
        assertThat(vaga).hasFieldOrPropertyWithValue("amount", 20D);
        assertThat(vaga).hasFieldOrPropertyWithValue("data_oferta_inicio", "26/05/2022");
        assertThat(vaga).hasFieldOrPropertyWithValue("data_oferta_fim", "30/05/2022");
        assertThat(vaga).hasFieldOrPropertyWithValue("nome", "Vaga Desenvolvedor Java");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_vaga() {
        Vaga vaga = getVaga();
        vagaRepository.save(vaga);

        Optional<Vaga> found = vagaRepository.findById(vaga.getId());
        assertThat(found.get()).isEqualTo(vaga);
    }

    @Test
    public void should_found_null_vaga() {
        Optional<Vaga> fromDb = vagaRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnVaga() {
        Vaga vaga = getVaga();
        vagaRepository.save(vaga);

        Vaga fromDb = vagaRepository.findById(vaga.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(vaga);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Vaga fromDb = vagaRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Disabled
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Vaga vaga = getVaga();
        Vaga vaga1 = getVaga();
        Vaga vaga2 = getVaga();

        vagaRepository.save(vaga);
        vagaRepository.save(vaga1);
        vagaRepository.save(vaga2);

        Iterator<Vaga> allCountries = vagaRepository.findAll().iterator();
        List<Vaga> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("aplicado").contains(1);
        assertThat(countries).extracting("amount").contains(20D);
        assertThat(countries).extracting("data_oferta_inicio").contains("26/05/2022");
        assertThat(countries).extracting("nome").contains("Vaga Desenvolvedor Java");
    }
}
