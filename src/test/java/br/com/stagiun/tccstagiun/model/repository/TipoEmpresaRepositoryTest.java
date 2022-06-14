package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.TipoEmpresa;
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
public class TipoEmpresaRepositoryTest {

    @Autowired
    private TipoEmpresaRepository tipoEmpresaRepository;

    private TipoEmpresa getTipoEmpresa() {
        return TipoEmpresa.builder()
                .descricao("Tecnologia")
                .build();
    }

    @Test
    public void should_find_no_tipoEmpresa_if_repository_is_empty() {
        Iterable<TipoEmpresa> seeds = tipoEmpresaRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_tipoEmpresa() {
        TipoEmpresa tipoEmpresa = tipoEmpresaRepository.save(getTipoEmpresa());

        assertThat(tipoEmpresa).hasFieldOrPropertyWithValue("descricao", "Tecnologia");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_tipoEmpresa() {
        TipoEmpresa tipoEmpresa = getTipoEmpresa();
        tipoEmpresaRepository.save(tipoEmpresa);

        Optional<TipoEmpresa> found = tipoEmpresaRepository.findById(tipoEmpresa.getId());
        assertThat(found.get()).isEqualTo(tipoEmpresa);
    }

    @Test
    public void should_found_null_tipoEmpresa() {
        Optional<TipoEmpresa> fromDb = tipoEmpresaRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnTipoEmpresa() {
        TipoEmpresa tipoEmpresa = getTipoEmpresa();
        tipoEmpresaRepository.save(tipoEmpresa);

        TipoEmpresa fromDb = tipoEmpresaRepository.findById(tipoEmpresa.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(tipoEmpresa);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        TipoEmpresa fromDb = tipoEmpresaRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        TipoEmpresa tipoEmpresa = getTipoEmpresa();
        TipoEmpresa tipoEmpresa1 = getTipoEmpresa();
        TipoEmpresa tipoEmpresa2 = getTipoEmpresa();

        tipoEmpresaRepository.save(tipoEmpresa);
        tipoEmpresaRepository.save(tipoEmpresa1);
        tipoEmpresaRepository.save(tipoEmpresa2);

        Iterator<TipoEmpresa> allCountries = tipoEmpresaRepository.findAll().iterator();
        List<TipoEmpresa> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("descricao").contains("Tecnologia");
    }

}
