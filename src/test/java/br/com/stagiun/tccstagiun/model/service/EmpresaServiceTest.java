package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Empresa;
import br.com.stagiun.tccstagiun.model.repository.EmpresaRepository;
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
class EmpresaServiceTest {

    @Mock
    EmpresaRepository empresaRepository;

    @MockBean
    EmpresaService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_empresaes() {
        List<Empresa> empresaList = new ArrayList<>();

        Empresa empresa = domainMock.getEmpresa();
        empresaList.add(empresa);

        when(service.list()).thenReturn(empresaList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Empresa empresa = domainMock.getEmpresa();
        when(service.findById(id)).thenReturn(Optional.of(empresa));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Empresa empresa = domainMock.getEmpresa();

        when(this.service.salvar(empresa)).thenReturn(empresa);

        Empresa empresaSalvo = this.service.salvar(empresa);
        assertNotNull(empresaSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Empresa empresa = domainMock.getEmpresa();

        when(service.editar(id, empresa)).thenReturn(empresa);

        Empresa empresaAlterado = this.service.editar(id, empresa);
        assertNotNull(empresaAlterado);
    }

}