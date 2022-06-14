package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Empresa;
import br.com.stagiun.tccstagiun.model.repository.EmpresaRepository;
import br.com.stagiun.tccstagiun.model.service.impl.EmpresaImpl;
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
public class EmpresaServiceTest {

    @InjectMocks
    EmpresaImpl empresaService;

    @Mock
    EmpresaRepository empresaRepository;

     private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Empresa> list = new ArrayList<>();

        Empresa empresa = domainMock.getEmpresa();
        Empresa empresa1 = domainMock.getEmpresa();
        Empresa empresa2 = domainMock.getEmpresa();

        list.add(empresa);
        list.add(empresa1);
        list.add(empresa2);

        when(empresaRepository.findAll()).thenReturn(list);

        // test
        List<Empresa> urls = empresaService.list();

        assertEquals(3, urls.size());
        verify(empresaRepository, times(1)).findAll();
    }

    @Test
    public void getEmpresaByIdTest() {
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(domainMock.getEmpresa()));

        Optional<Empresa> empresa = empresaService.findById(1L);

        assertEquals("Stagiun", empresa.get().getNomeFantasia());
        assertEquals("ADS-Stagiun", empresa.get().getRazaoSocial());
        assertEquals(22244455, empresa.get().getCnpj());
        assertEquals(32324545, empresa.get().getTelefone());
        assertEquals("stagiun@gmail.com", empresa.get().getEmail());
    }

    @Test
    public void getFindEmpresaByShortIdTest() {
        Empresa empresa = domainMock.getEmpresa();
        when(empresaService.findById(1L)).thenReturn(Optional.ofNullable(empresa));

        Optional<Empresa> result = empresaService.findById(1L);

        assertEquals("Stagiun", empresa.getNomeFantasia());
        assertEquals("ADS-Stagiun", empresa.getRazaoSocial());
        assertEquals(22244455, empresa.getCnpj());
        assertEquals(32324545, empresa.getTelefone());
        assertEquals("stagiun@gmail.com", empresa.getEmail());
    }

    @Test
    public void createEmpresaTest() throws ResourceFoundException {
        Empresa url = domainMock.getEmpresa();
        empresaService.salvar(url);

        verify(empresaRepository, times(3)).save(url);
    }

    @Test
    public void createAndStoreDicaTest() throws ResourceFoundException {
        Empresa empresa = domainMock.getEmpresa();
        empresaService.salvar(empresa);

        when(empresaService.salvar(empresa)).thenReturn(empresa);
        Empresa result = empresaService.salvar(empresa);

        assertEquals("Stagiun", result.getNomeFantasia());
        assertEquals("ADS-Stagiun", result.getRazaoSocial());
        assertEquals(22244455, result.getCnpj());
        assertEquals(32324545, result.getTelefone());
        assertEquals("stagiun@gmail.com", result.getEmail());
    }
}
