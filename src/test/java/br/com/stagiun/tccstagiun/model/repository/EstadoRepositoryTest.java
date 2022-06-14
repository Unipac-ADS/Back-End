package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.Estado;
import br.com.stagiun.tccstagiun.model.domain.Pais;
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
public class EstadoRepositoryTest {

    @Autowired
    private EstadoRepository estadoRepository;

    private Estado getEstado() {
        return Estado.builder()
                .descricao("Minas Gerais")
                .build();
    }

    @Test
    public void should_find_no_estado_if_repository_is_empty() {
        Iterable<Estado> seeds = estadoRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_estado() {
        Estado estado = estadoRepository.save(getEstado());

        assertThat(estado).hasFieldOrPropertyWithValue("descricao", "Minas Gerais");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_estado() {
        Estado estado = getEstado();
        estadoRepository.save(estado);

        Optional<Estado> found = estadoRepository.findById(estado.getId());
        assertThat(found.get()).isEqualTo(estado);
    }

    @Test
    public void should_found_null_estado() {
        Optional<Estado> fromDb = estadoRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnEstado() {
        Estado estado = getEstado();
        estadoRepository.save(estado);

        Estado fromDb = estadoRepository.findById(estado.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(estado);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Estado fromDb = estadoRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Estado estado = getEstado();
        Estado estado1 = getEstado();
        Estado estado2 = getEstado();

        estadoRepository.save(estado);
        estadoRepository.save(estado1);
        estadoRepository.save(estado2);

        Iterator<Estado> allCountries = estadoRepository.findAll().iterator();
        List<Estado> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("Minas Gerais");
    }
}
