package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.TccApplication;
import br.com.stagiun.tccstagiun.model.domain.Usuario;
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
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario getUsuario() {
        return Usuario.builder()
                .nome("Playboyzinho")
                .email("palyboyzinho@gmail.com")
                .senha("Mjolnir")
                .build();
    }

    @Test
    public void should_find_no_usuario_if_repository_is_empty() {
        Iterable<Usuario> seeds = usuarioRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    public void should_store_a_usuario() {
        Usuario turma = usuarioRepository.save(getUsuario());

        assertThat(turma).hasFieldOrPropertyWithValue("nome", "Playboyzinho");
        assertThat(turma).hasFieldOrPropertyWithValue("email", "palyboyzinho@gmail.com");
        assertThat(turma).hasFieldOrPropertyWithValue("senha", "Mjolnir");
    }

    @Test
    public void testValidationWhenNoNameThenThrowException() {
        //TODO - colocar um assert Throws
    }

    @Test
    public void should_found_store_a_usuario() {
        Usuario usuario = getUsuario();
        usuarioRepository.save(usuario);

        Optional<Usuario> found = usuarioRepository.findById(usuario.getId());
        assertThat(found.get()).isEqualTo(usuario);
    }

    @Test
    public void should_found_null_usuario() {
        Optional<Usuario> fromDb = usuarioRepository.findById(1L);
        assertThat(fromDb).isEmpty();
        assertFalse(fromDb.isPresent());
    }

    @Test
    public void whenFindById_thenReturnUsuario() {
        Usuario usuario = getUsuario();
        usuarioRepository.save(usuario);

        Usuario fromDb = usuarioRepository.findById(usuario.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(usuario);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Usuario fromDb = usuarioRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCompanies_whenFindAll_thenReturnAllCountries() {
        Usuario usuario = getUsuario();
        Usuario usuario1 = getUsuario();
        Usuario usuario2 = getUsuario();

        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        Iterator<Usuario> allCountries = usuarioRepository.findAll().iterator();
        List<Usuario> countries = new ArrayList<>();
        allCountries.forEachRemaining(c -> countries.add(c));

        assertEquals(countries.size(), 3);
        assertThat(countries).extracting("nome").contains("Playboyzinho");
        assertThat(countries).extracting("email").contains("palyboyzinho@gmail.com");
        assertThat(countries).extracting("senha").contains("Mjolnir");
    }
}
