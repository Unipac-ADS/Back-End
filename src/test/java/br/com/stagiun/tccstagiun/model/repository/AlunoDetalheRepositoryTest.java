package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhe;
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
@ContextConfiguration(classes = {TccApplication.class})
//@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@ActiveProfiles(value = "local")
//@TestPropertySource(locations = "classpath:test.properties")
//@Profile("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlunoDetalheRepositoryTest {

    @Autowired
    private AlunoDetalhesRepository alunoDetalhesRepository;

    private AlunoDetalhe getAlunoDetalhes() {
        return AlunoDetalhe.builder()
                .anoDeInicioCurso("07/02/2020")
                .anoDeConclusaoCurso("17/07/2022")
                .experiencia("Dev Full Stack")
                .infoAdicionais("Full Stack")
                .deficiencia(0)
                .sobre("Full Stack")
                .linkedin("linkedin.com/in/thor")
                .github("github/thor")
                .instagram("Thor Filho de Odin")
                .twitter("Thor Filho de Odin")
                .fileCurriculo("CvThor")
                .build();
    }

    @Test
    public void should_find_no_alunoDetalhes_if_repository_is_empty() {
        Iterable<AlunoDetalhe> seeds = alunoDetalhesRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Disabled
    public void should_store_a_acompanhamento() {
        AlunoDetalhe alunoDetalhes = alunoDetalhesRepository.save(getAlunoDetalhes());

        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue("ano_de_inicio_curso", "07/02/2020");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue("ano_de_conclusao_curso", "17/07/2022");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue("experiencia", "Dev Full Stack");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue("info_adicionais", "Full Stack");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue("deficiencia", 0);
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue("sobre", "Full Stack");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue("linkedin", "linkedin.com/in/thor");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue("github", "github/thor");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue("instagram", "Thor Filho de Odin");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue("twitter", "Thor Filho de Odin");
        assertThat(alunoDetalhes).hasFieldOrPropertyWithValue("file_curriculo", "CvThor");

    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_alunoDetalhes() {
        AlunoDetalhe alunoDetalhes = getAlunoDetalhes();
        alunoDetalhesRepository.save(alunoDetalhes);

        Optional<AlunoDetalhe> found = alunoDetalhesRepository.findById(alunoDetalhes.getId());
        assertThat(found.get()).isEqualTo(alunoDetalhes);
    }

    @Test
    public void should_found_null_alunoDetalhes() {
        Optional<AlunoDetalhe> fromDb = alunoDetalhesRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnAlunoDetalhes() {
        AlunoDetalhe alunoDetalhes = getAlunoDetalhes();
        alunoDetalhesRepository.save(alunoDetalhes);

        AlunoDetalhe fromDb = alunoDetalhesRepository.findById(alunoDetalhes.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(alunoDetalhes);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        AlunoDetalhe fromDb = alunoDetalhesRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Disabled
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        AlunoDetalhe alunoDetalhes = getAlunoDetalhes();
        AlunoDetalhe alunoDetalhes2 = getAlunoDetalhes();
        AlunoDetalhe alunoDetalhes3 = getAlunoDetalhes();

        alunoDetalhesRepository.save(alunoDetalhes);
        alunoDetalhesRepository.save(alunoDetalhes2);
        alunoDetalhesRepository.save(alunoDetalhes3);

        Iterator<AlunoDetalhe> allCountries = alunoDetalhesRepository.findAll().iterator();
        List<AlunoDetalhe> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("ano_de_inicio_curso").contains("07/02/2020");
        assertThat(countries).extracting("ano_de_conclusao_curso").contains("17/07/2022");
        assertThat(countries).extracting("experiencia").contains("Dev Full Stack");
        assertThat(countries).extracting("info_adicionais").contains("Full Stack");
        assertThat(countries).extracting("deficiencia").contains(0);
        assertThat(countries).extracting("sobre").contains("Full Stack");
        assertThat(countries).extracting("linkedin").contains("linkedin.com/in/thor");
        assertThat(countries).extracting("github").contains("github/thor");
        assertThat(countries).extracting("instagram").contains("Thor Filho de Odin");
        assertThat(countries).extracting("twitter").contains("Thor Filho de Odin");
        assertThat(countries).extracting("file_curriculo").contains("CvThor");

    }

}
