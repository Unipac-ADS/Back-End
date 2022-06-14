package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.TipoEmpresa;
import br.com.stagiun.tccstagiun.model.repository.TipoEmpresaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(value = "local")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TipoEmpresaServiceTest {

    @Mock
    TipoEmpresaRepository tipoEmpresaRepository;

    @MockBean
    TipoEmpresaService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_tipoEmpresaes() {
        List<TipoEmpresa> tipoEmpresaList = new ArrayList<>();

        TipoEmpresa tipoEmpresa = domainMock.getTipoEmpresa();
        tipoEmpresaList.add(tipoEmpresa);

        when(service.list()).thenReturn(tipoEmpresaList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        TipoEmpresa tipoEmpresa = domainMock.getTipoEmpresa();
        when(service.findById(id)).thenReturn(Optional.of(tipoEmpresa));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        TipoEmpresa tipoEmpresa = domainMock.getTipoEmpresa();

        when(this.service.salvar(tipoEmpresa)).thenReturn(tipoEmpresa);

        TipoEmpresa tipoEmpresaSalvo = this.service.salvar(tipoEmpresa);
        assertNotNull(tipoEmpresaSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        TipoEmpresa tipoEmpresa = domainMock.getTipoEmpresa();

        when(service.editar(id, tipoEmpresa)).thenReturn(tipoEmpresa);

        TipoEmpresa tipoEmpresaAlterado = this.service.editar(id, tipoEmpresa);
        assertNotNull(tipoEmpresaAlterado);
    }

}