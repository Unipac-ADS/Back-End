package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Perfil;
import br.com.stagiun.tccstagiun.model.repository.PerfilRepository;
import br.com.stagiun.tccstagiun.model.service.impl.PerfilServiceImpl;
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
public class PerfilServiceTest {

    @InjectMocks
    PerfilServiceImpl perfilService;

    @Mock
    PerfilRepository perfilRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Perfil> list = new ArrayList<>();

        Perfil perfil = domainMock.getPerfil();
        Perfil perfil1 = domainMock.getPerfil();
        Perfil perfil2 = domainMock.getPerfil();

        list.add(perfil);
        list.add(perfil1);
        list.add(perfil2);

        when(perfilRepository.findAll()).thenReturn(list);

        // test
        List<Perfil> urls = perfilService.list();

        assertEquals(3, urls.size());
        verify(perfilRepository, times(1)).findAll();
    }

    @Test
    public void getPerfilByIdTest() {
        when(perfilRepository.findById(1L)).thenReturn(Optional.of(domainMock.getPerfil()));

        Optional<Perfil> perfil = perfilService.findById(1L);

        assertEquals("Aluno", perfil.get().getDescricao());
    }

    @Test
    public void getFindPerfilByShortIdTest() {
        Perfil perfil = domainMock.getPerfil();
        when(perfilService.findById(1L)).thenReturn(Optional.ofNullable(perfil));

        Optional<Perfil> result = perfilService.findById(1L);

        assertEquals("Aluno", perfil.getDescricao());
    }

    @Test
    public void createPerfilTest() throws ResourceFoundException {
        Perfil url = domainMock.getPerfil();
        perfilService.salvar(url);

        verify(perfilRepository, times(1)).save(url);
    }

    @Test
    public void createAndStorePerfilTest() throws ResourceFoundException {
        Perfil perfil = domainMock.getPerfil();
        perfilService.salvar(perfil);

        when(perfilService.salvar(perfil)).thenReturn(perfil);
        Perfil result = perfilService.salvar(perfil);

        assertEquals("Aluno", result.getDescricao());
    }
}
