package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Faculdade;
import br.com.stagiun.tccstagiun.model.repository.FaculdadeRepository;
import br.com.stagiun.tccstagiun.model.service.impl.FaculdadeServiceImpl;
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
public class FaculdadeServiceTest {

    @InjectMocks
    FaculdadeServiceImpl faculdadeService;

    @Mock
    FaculdadeRepository faculdadeRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Faculdade> list = new ArrayList<>();

        Faculdade faculdade = domainMock.getFaculdade();
        Faculdade faculdade1 = domainMock.getFaculdade();
        Faculdade faculdade2 = domainMock.getFaculdade();

        list.add(faculdade);
        list.add(faculdade1);
        list.add(faculdade2);

        when(faculdadeRepository.findAll()).thenReturn(list);

        // test
        List<Faculdade> urls = faculdadeService.list();

        assertEquals(3, urls.size());
        verify(faculdadeRepository, times(1)).findAll();
    }

    @Test
    public void getFaculdadeByIdTest() {
        when(faculdadeRepository.findById(1L)).thenReturn(Optional.of(domainMock.getFaculdade()));

        Optional<Faculdade> faculdade = faculdadeService.findById(1L);

        assertEquals("Unipac", faculdade.get().getNome());
    }

    @Test
    public void getFindFaculdadeByShortIdTest() {
        Faculdade faculdade = domainMock.getFaculdade();
        when(faculdadeService.findById(1L)).thenReturn(Optional.ofNullable(faculdade));

        Optional<Faculdade> result = faculdadeService.findById(1L);

        assertEquals("Unipac", faculdade.getNome());
    }

    @Test
    public void createFaculdadeTest() throws ResourceFoundException {
        Faculdade url = domainMock.getFaculdade();
        faculdadeService.salvar(url);

        verify(faculdadeRepository, times(3)).save(url);
    }

    @Test
    public void createAndStoreFaculdadeTest() throws ResourceFoundException {
        Faculdade faculdade = domainMock.getFaculdade();
        faculdadeService.salvar(faculdade);

        when(faculdadeService.salvar(faculdade)).thenReturn(faculdade);
        Faculdade result = faculdadeService.salvar(faculdade);

        assertEquals("Unipac", result.getNome());
    }
}
