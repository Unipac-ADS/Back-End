package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Endereco;
import br.com.stagiun.tccstagiun.model.domain.Pais;
import br.com.stagiun.tccstagiun.model.repository.EnderecoRepository;
import br.com.stagiun.tccstagiun.model.service.impl.EnderecoServiceImpl;
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
public class EnderecoServiceTest {

    @InjectMocks
    EnderecoServiceImpl enderecoService;

    @Mock
    EnderecoRepository enderecoRepository;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Endereco> list = new ArrayList<>();

        Endereco endereco = domainMock.getEndereco();
        Endereco endereco1 = domainMock.getEndereco();
        Endereco endereco2 = domainMock.getEndereco();

        list.add(endereco);
        list.add(endereco1);
        list.add(endereco2);

        when(enderecoRepository.findAll()).thenReturn(list);

        // test
        List<Endereco> urls = enderecoService.list();

        assertEquals(3, urls.size());
        verify(enderecoRepository, times(1)).findAll();
    }

    @Test
    public void getEnderecoByIdTest() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(domainMock.getEndereco()));

        Optional<Endereco> endereco = enderecoService.findById(1L);

        assertEquals("Afonso Pena", endereco.get().getRua());
        assertEquals("Av", endereco.get().getTipo());
        assertEquals("323", endereco.get().getNumero());
    }

    @Test
    public void getFindEnderecoByShortIdTest() {
        Endereco endereco = domainMock.getEndereco();
        when(enderecoService.findById(1L)).thenReturn(Optional.ofNullable(endereco));

        Optional<Endereco> result = enderecoService.findById(1L);

        assertEquals("Afonso Pena", result.get().getRua());
        assertEquals("Av", result.get().getTipo());
        assertEquals("323", result.get().getNumero());
    }

    @Test
    public void createEnderecoTest() throws ResourceFoundException {
        Endereco url = domainMock.getEndereco();
        enderecoService.salvar(url);

        verify(enderecoRepository, times(3)).save(url);
    }

    @Test
    public void createAndStoreEnderecoTest() throws ResourceFoundException {
        Endereco endereco = domainMock.getEndereco();
        enderecoService.salvar(endereco);

        when(enderecoService.salvar(endereco)).thenReturn(endereco);
        Endereco result = enderecoService.salvar(endereco);

        assertEquals("Afonso Pena", result.getRua());
        assertEquals("Av", result.getTipo());
        assertEquals("323", result.getNumero());
    }
}
