package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
public class AcompanhamentoRepositoryTest {

    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;

    private Acompanhamento getAcompanhamento() {
        return Acompanhamento.builder()
                .dataAplicacao("26/05/2022")
                .build();
    }

    @Test
    public void should_find_no_acompanhamento_if_repository_is_empty() {
        Iterable<Acompanhamento> seeds = acompanhamentoRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Disabled
    public void should_store_a_acompanhamento() {
        Acompanhamento acompanhamento = acompanhamentoRepository.save(getAcompanhamento());

        assertThat(acompanhamento).hasFieldOrPropertyWithValue("data_aplicacao","26/05/2022");

    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
      //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_acompanhamento() {
        Acompanhamento acompanhamento = getAcompanhamento();
        acompanhamentoRepository.save(acompanhamento);

        Optional<Acompanhamento> found = acompanhamentoRepository.findById(acompanhamento.getId());
        assertThat(found.get()).isEqualTo(acompanhamento);
    }

    @Test
    public void should_found_null_acompanhamento() {
        Optional<Acompanhamento> fromDb = acompanhamentoRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnAcompanhamento() {
        Acompanhamento acompanhamento = getAcompanhamento();
        acompanhamentoRepository.save(acompanhamento);

        Acompanhamento fromDb = acompanhamentoRepository.findById(acompanhamento.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(acompanhamento);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Acompanhamento fromDb = acompanhamentoRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Acompanhamento acompanhamento = getAcompanhamento();
        Acompanhamento acompanhamento2 = getAcompanhamento();
        Acompanhamento acompanhamento3 = getAcompanhamento();

        acompanhamentoRepository.save(acompanhamento);
        acompanhamentoRepository.save(acompanhamento2);
        acompanhamentoRepository.save(acompanhamento3);

        Iterator<Acompanhamento> allCountries = acompanhamentoRepository.findAll().iterator();
        List<Acompanhamento> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        //assertThat(countries).extracting("data_aplicacao").contains("26/05/2022");

    }

}
