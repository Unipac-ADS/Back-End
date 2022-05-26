package br.com.stagiun.tccstagiun.services;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;
import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhes;
import br.com.stagiun.tccstagiun.model.repository.AlunoDetalhesRepository;
import br.com.stagiun.tccstagiun.model.service.impl.AlunoDetalhesImpl;
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
public class AlunoDetalhesServiceTest {

    @InjectMocks
    AlunoDetalhesImpl alunoDetalhesService;

    @Mock
    AlunoDetalhesRepository alunoDetalhesRepository;

    //public ExpectedException thrown = ExpectedException.none();

    private AlunoDetalhes getAlunoDetalhes() {
        return AlunoDetalhes.builder()
                .ano_de_inicio_curso("07/02/2020")
                .ano_de_conclusao_curso("17/07/2022")
                .experiencia("Dev Full Stack")
                .info_adicionais("Full Stack")
                .deficiencia(0)
                .sobre("Full Stack")
                .linkedin("linkedin.com/in/thor")
                .github("github/thor")
                .instagram("Thor Filho de Odin")
                .twitter("Thor Filho de Odin")
                .file_curriculo("CvThor")
                .build();
    }

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<AlunoDetalhes> list = new ArrayList<>();

        AlunoDetalhes alunoDetalhes = getAlunoDetalhes();
        AlunoDetalhes alunoDetalhes1 = getAlunoDetalhes();
        AlunoDetalhes alunoDetalhes2 = getAlunoDetalhes();

        list.add(alunoDetalhes);
        list.add(alunoDetalhes1);
        list.add(alunoDetalhes2);

        when(alunoDetalhesRepository.findAll()).thenReturn(list);

        // test
        List<AlunoDetalhes> urls = alunoDetalhesService.list();

        assertEquals(3, urls.size());
        verify(alunoDetalhesRepository, times(1)).findAll();
    }

    @Test
    public void getAlunoDetalhesByIdTest() {
        when(alunoDetalhesRepository.findById(1L)).thenReturn(Optional.of(getAlunoDetalhes()));

        Optional<AlunoDetalhes> alunoDetalhes = alunoDetalhesService.findById(1L);

        assertEquals("07/02/2020", alunoDetalhes.get().getAno_de_inicio_curso());
        assertEquals("17/07/2022", alunoDetalhes.get().getAno_de_conclusao_curso());
        assertEquals("Dev Full Stack", alunoDetalhes.get().getExperiencia());
        assertEquals("Full Stack", alunoDetalhes.get().getInfo_adicionais());
        assertEquals(0, alunoDetalhes.get().getDeficiencia());
        assertEquals("Full Stack", alunoDetalhes.get().getSobre());
        assertEquals("linkedin.com/in/thor", alunoDetalhes.get().getLinkedin());
        assertEquals("github/thor", alunoDetalhes.get().getGithub());
        assertEquals("Thor Filho de Odin", alunoDetalhes.get().getInstagram());
        assertEquals("Thor Filho de Odin", alunoDetalhes.get().getTwitter());
        assertEquals("CvThor", alunoDetalhes.get().getFile_curriculo());

    }

    @Test
    public void getFindAlunoDetalhesByShortIdTest() {
        AlunoDetalhes alunoDetalhes = getAlunoDetalhes();
        when(alunoDetalhesService.findById(1L)).thenReturn(Optional.ofNullable(alunoDetalhes));

        Optional<AlunoDetalhes> result = alunoDetalhesService.findById(1L);

        assertEquals("07/02/2020", alunoDetalhes.getAno_de_inicio_curso());
        assertEquals("17/07/2022", alunoDetalhes.getAno_de_conclusao_curso());
        assertEquals("Dev Full Stack", alunoDetalhes.getExperiencia());
        assertEquals("Full Stack", alunoDetalhes.getInfo_adicionais());
        assertEquals(0, alunoDetalhes.getDeficiencia());
        assertEquals("Full Stack", alunoDetalhes.getSobre());
        assertEquals("linkedin.com/in/thor", alunoDetalhes.getLinkedin());
        assertEquals("github/thor", alunoDetalhes.getGithub());
        assertEquals("Thor Filho de Odin", alunoDetalhes.getInstagram());
        assertEquals("Thor Filho de Odin", alunoDetalhes.getTwitter());
        assertEquals("CvThor", alunoDetalhes.getFile_curriculo());
    }

    @Test
    public void createAlunoDetalhesTest() throws ResourceFoundException {
        AlunoDetalhes url = getAlunoDetalhes();
        alunoDetalhesService.salvar(url);

        verify(alunoDetalhesRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreAcompanhamentoTest() throws ResourceFoundException {
        AlunoDetalhes alunoDetalhes = getAlunoDetalhes();
        alunoDetalhesService.salvar(alunoDetalhes);

        when(alunoDetalhesService.salvar(alunoDetalhes)).thenReturn(alunoDetalhes);
        AlunoDetalhes result = alunoDetalhesService.salvar(alunoDetalhes);

        assertEquals("07/02/2020", result.getAno_de_inicio_curso());
        assertEquals("17/07/2022", result.getAno_de_conclusao_curso());
        assertEquals("Dev Full Stack", result.getExperiencia());
        assertEquals("Full Stack", result.getInfo_adicionais());
        assertEquals(0, result.getDeficiencia());
        assertEquals("Full Stack", result.getSobre());
        assertEquals("linkedin.com/in/thor", result.getLinkedin());
        assertEquals("github/thor", result.getGithub());
        assertEquals("Thor Filho de Odin", result.getInstagram());
        assertEquals("Thor Filho de Odin", result.getTwitter());
        assertEquals("CvThor", result.getFile_curriculo());
    }
}
