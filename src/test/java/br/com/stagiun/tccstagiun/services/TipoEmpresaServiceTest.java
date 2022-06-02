package br.com.stagiun.tccstagiun.services;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.TipoEmpresa;
import br.com.stagiun.tccstagiun.model.repository.TipoEmpresaRepository;
import br.com.stagiun.tccstagiun.model.service.impl.TipoEmpresaServiceImpl;
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
public class TipoEmpresaServiceTest {

    @InjectMocks
    TipoEmpresaServiceImpl tipoEmpresaService;

    @Mock
    TipoEmpresaRepository tipoEmpresaRepository;

    //public ExpectedException thrown = ExpectedException.none();

    private TipoEmpresa getTipoEmpresa() {
        return TipoEmpresa.builder()
                .descricao("Tecnologia")
                .build();
    }

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<TipoEmpresa> list = new ArrayList<>();

        TipoEmpresa tipoEmpresa = getTipoEmpresa();
        TipoEmpresa tipoEmpresa1 = getTipoEmpresa();
        TipoEmpresa tipoEmpresa2 = getTipoEmpresa();

        list.add(tipoEmpresa);
        list.add(tipoEmpresa1);
        list.add(tipoEmpresa2);

        when(tipoEmpresaRepository.findAll()).thenReturn(list);

        // test
        List<TipoEmpresa> urls = tipoEmpresaService.list();

        assertEquals(3, urls.size());
        verify(tipoEmpresaRepository, times(1)).findAll();
    }

    @Test
    public void getTipoEmpresaByIdTest() {
        when(tipoEmpresaRepository.findById(1L)).thenReturn(Optional.of(getTipoEmpresa()));

        Optional<TipoEmpresa> tipoEmpresa = tipoEmpresaService.findById(1L);

        assertEquals("Tecnologia", tipoEmpresa.get().getDescricao());
    }

    @Test
    public void getFindTipoEmpresaByShortIdTest() {
        TipoEmpresa tipoEmpresa = getTipoEmpresa();
        when(tipoEmpresaService.findById(1L)).thenReturn(Optional.ofNullable(tipoEmpresa));

        Optional<TipoEmpresa> result = tipoEmpresaService.findById(1L);

        assertEquals("Tecnologia", tipoEmpresa.getDescricao());
    }

    @Test
    public void createTipoEmpresaTest() throws ResourceFoundException {
        TipoEmpresa url = getTipoEmpresa();
        tipoEmpresaService.salvar(url);

        verify(tipoEmpresaRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreTipoEmpresaTest() throws ResourceFoundException {
        TipoEmpresa tipoEmpresa = getTipoEmpresa();
        tipoEmpresaService.salvar(tipoEmpresa);

        when(tipoEmpresaService.salvar(tipoEmpresa)).thenReturn(tipoEmpresa);
        TipoEmpresa result = tipoEmpresaService.salvar(tipoEmpresa);

        assertEquals("Tecnologia", result.getDescricao());
    }
}
