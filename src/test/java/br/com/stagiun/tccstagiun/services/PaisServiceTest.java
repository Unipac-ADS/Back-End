package br.com.stagiun.tccstagiun.services;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Pais;
import br.com.stagiun.tccstagiun.model.repository.PaisRepository;
import br.com.stagiun.tccstagiun.model.service.impl.PaisServiceImpl;
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
public class PaisServiceTest {

    @InjectMocks
    PaisServiceImpl paisService;

    @Mock
    PaisRepository paisRepository;

    //public ExpectedException thrown = ExpectedException.none();

    private Pais getPais() {
        return Pais.builder()
                .descricao("Brasil")
                .build();
    }

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Pais> list = new ArrayList<>();

        Pais pais = getPais();
        Pais pais1 = getPais();
        Pais pais2 = getPais();

        list.add(pais);
        list.add(pais1);
        list.add(pais2);

        when(paisRepository.findAll()).thenReturn(list);

        // test
        List<Pais> urls = paisService.list();

        assertEquals(3, urls.size());
        verify(paisRepository, times(1)).findAll();
    }

    @Test
    public void getPaisByIdTest() {
        when(paisRepository.findById(1L)).thenReturn(Optional.of(getPais()));

        Optional<Pais> pais = paisService.findById(1L);

        assertEquals("Brasil", pais.get().getDescricao());
    }

    @Test
    public void getFindPaisByShortIdTest() {
        Pais pais = getPais();
        when(paisService.findById(1L)).thenReturn(Optional.ofNullable(pais));

        Optional<Pais> result = paisService.findById(1L);

        assertEquals("Brasil", pais.getDescricao());
    }

    @Test
    public void createPaisTest() throws ResourceFoundException {
        Pais url = getPais();
        paisService.salvar(url);

        verify(paisRepository, times(1)).save(url);
    }

    @Test
    public void createAndStorePaisTest() throws ResourceFoundException {
        Pais pais = getPais();
        paisService.salvar(pais);

        when(paisService.salvar(pais)).thenReturn(pais);
        Pais result = paisService.salvar(pais);

        assertEquals("Brasil", result.getDescricao());
    }
}
