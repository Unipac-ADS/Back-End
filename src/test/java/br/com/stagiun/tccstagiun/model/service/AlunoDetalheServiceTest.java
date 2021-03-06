package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhe;
import br.com.stagiun.tccstagiun.model.repository.AlunoDetalheRepository;
import br.com.stagiun.tccstagiun.model.service.impl.AlunoDetalheImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

//@Profile("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(value = "local")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlunoDetalheServiceTest {

    @InjectMocks
    AlunoDetalheImpl alunoDetalhesService;

    @Mock
    AlunoDetalheRepository alunoDetalheRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<AlunoDetalhe> list = new ArrayList<>();

        AlunoDetalhe alunoDetalhes = domainMock.getAlunoDetalhes();
        AlunoDetalhe alunoDetalhes1 = domainMock.getAlunoDetalhes();
        AlunoDetalhe alunoDetalhes2 = domainMock.getAlunoDetalhes();

        list.add(alunoDetalhes);
        list.add(alunoDetalhes1);
        list.add(alunoDetalhes2);

        when(alunoDetalheRepository.findAll()).thenReturn(list);

        // test
        List<AlunoDetalhe> urls = alunoDetalhesService.list();

        assertEquals(3, urls.size());
        verify(alunoDetalheRepository, times(1)).findAll();
    }

    @Test
    public void getAlunoDetalhesByIdTest() {
        when(alunoDetalheRepository.findById(1L)).thenReturn(Optional.of(domainMock.getAlunoDetalhes()));

        Optional<AlunoDetalhe> alunoDetalhes = alunoDetalhesService.findById(1L);

        assertEquals("07/02/2020", alunoDetalhes.get().getAnoDeInicioCurso());
        assertEquals("17/07/2022", alunoDetalhes.get().getAnoDeConclusaoCurso());
        assertEquals("Dev Full Stack", alunoDetalhes.get().getExperiencia());
        assertEquals("Full Stack", alunoDetalhes.get().getInfoAdicionais());
        assertEquals(0, alunoDetalhes.get().getDeficiencia());
        assertEquals("Full Stack", alunoDetalhes.get().getSobre());
        assertEquals("linkedin.com/in/thor", alunoDetalhes.get().getLinkedin());
        assertEquals("github/thor", alunoDetalhes.get().getGithub());
        assertEquals("Thor Filho de Odin", alunoDetalhes.get().getInstagram());
        assertEquals("Thor Filho de Odin", alunoDetalhes.get().getTwitter());
        assertEquals("CvThor", alunoDetalhes.get().getFileCurriculo());

    }

    @Test
    public void getFindAlunoDetalhesByShortIdTest() {
        AlunoDetalhe alunoDetalhes = domainMock.getAlunoDetalhes();
        when(alunoDetalhesService.findById(1L)).thenReturn(Optional.ofNullable(alunoDetalhes));

        Optional<AlunoDetalhe> result = alunoDetalhesService.findById(1L);

        assertEquals("07/02/2020", alunoDetalhes.getAnoDeInicioCurso());
        assertEquals("17/07/2022", alunoDetalhes.getAnoDeConclusaoCurso());
        assertEquals("Dev Full Stack", alunoDetalhes.getExperiencia());
        assertEquals("Full Stack", alunoDetalhes.getInfoAdicionais());
        assertEquals(0, alunoDetalhes.getDeficiencia());
        assertEquals("Full Stack", alunoDetalhes.getSobre());
        assertEquals("linkedin.com/in/thor", alunoDetalhes.getLinkedin());
        assertEquals("github/thor", alunoDetalhes.getGithub());
        assertEquals("Thor Filho de Odin", alunoDetalhes.getInstagram());
        assertEquals("Thor Filho de Odin", alunoDetalhes.getTwitter());
        assertEquals("CvThor", alunoDetalhes.getFileCurriculo());
    }

    @Test
    public void createAlunoDetalhesTest() throws ResourceFoundException {
        AlunoDetalhe url = domainMock.getAlunoDetalhes();
        alunoDetalhesService.salvar(url);

        verify(alunoDetalheRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreAcompanhamentoTest() throws ResourceFoundException {
        AlunoDetalhe alunoDetalhe = domainMock.getAlunoDetalhes();
        alunoDetalhesService.salvar(alunoDetalhe);

        when(alunoDetalhesService.salvar(alunoDetalhe)).thenReturn(alunoDetalhe);
        AlunoDetalhe result = alunoDetalhesService.salvar(alunoDetalhe);

        assertEquals("07/02/2020", result.getAnoDeInicioCurso());
        assertEquals("17/07/2022", result.getAnoDeConclusaoCurso());
        assertEquals("Dev Full Stack", result.getExperiencia());
        assertEquals("Full Stack", result.getSobre());
        assertEquals(0, result.getDeficiencia());
        assertEquals("Full Stack", result.getSobre());
        assertEquals("linkedin.com/in/thor", result.getLinkedin());
        assertEquals("github/thor", result.getGithub());
        assertEquals("Thor Filho de Odin", result.getInstagram());
        assertEquals("Thor Filho de Odin", result.getTwitter());
        assertEquals("CvThor", result.getFileCurriculo());
    }
}
