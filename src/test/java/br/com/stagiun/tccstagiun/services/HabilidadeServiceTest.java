package br.com.stagiun.tccstagiun.services;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Habilidade;
import br.com.stagiun.tccstagiun.model.repository.HabilidadeRepository;
import br.com.stagiun.tccstagiun.model.service.impl.HabilidadeServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HabilidadeServiceTest {

    @InjectMocks
    HabilidadeServiceImpl habilidadeService;

    @Mock
    HabilidadeRepository habilidadeRepository;

    //public ExpectedException thrown = ExpectedException.none();

    private Habilidade getHabilidade() {
        return Habilidade.builder()
                .nome("Proativo")
                .build();
    }

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Habilidade> list = new ArrayList<>();

        Habilidade habilidade = getHabilidade();
        Habilidade habilidade1 = getHabilidade();
        Habilidade habilidade2 = getHabilidade();

        list.add(habilidade);
        list.add(habilidade1);
        list.add(habilidade2);

        when(habilidadeRepository.findAll()).thenReturn(list);

        // test
        List<Habilidade> urls = habilidadeService.list();

        assertEquals(3, urls.size());
        verify(habilidadeRepository, times(1)).findAll();
    }

    @Test
    public void getHabilidadeByIdTest() {
        when(habilidadeRepository.findById(1L)).thenReturn(Optional.of(getHabilidade()));

        Optional<Habilidade> habilidade = habilidadeService.findById(1L);

        assertEquals("Proativo", habilidade.get().getNome());
    }

    @Test
    public void getFindHabilidadeByShortIdTest() {
        Habilidade habilidade = getHabilidade();
        when(habilidadeService.findById(1L)).thenReturn(Optional.ofNullable(habilidade));

        Optional<Habilidade> result = habilidadeService.findById(1L);

        assertEquals("Proativo", habilidade.getNome());
    }

    @Test
    public void createHabilidadeTest() throws ResourceFoundException {
        Habilidade url = getHabilidade();
        habilidadeService.salvar(url);

        verify(habilidadeRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreHabilidadeTest() throws ResourceFoundException {
        Habilidade habilidade = getHabilidade();
        habilidadeService.salvar(habilidade);

        when(habilidadeService.salvar(habilidade)).thenReturn(habilidade);
        Habilidade result = habilidadeService.salvar(habilidade);

        assertEquals("Proativo", result.getNome());
    }
}
