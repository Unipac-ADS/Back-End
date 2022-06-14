package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Endereco;
import br.com.stagiun.tccstagiun.model.repository.EnderecoRepository;
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
class EnderecoServiceTest {

    @Mock
    EnderecoRepository enderecoRepository;

    @MockBean
    EnderecoService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    void find_by_countries_and_thenStatus200_and_all_enderecoes() {
        List<Endereco> enderecoList = new ArrayList<>();

        Endereco endereco = domainMock.getEndereco();
        enderecoList.add(endereco);

        when(service.list()).thenReturn(enderecoList);
        assertNotNull(service.list());
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;

        Endereco endereco = domainMock.getEndereco();
        when(service.findById(id)).thenReturn(Optional.of(endereco));
        assertNotNull(service.findById(id));
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Endereco endereco = domainMock.getEndereco();

        when(this.service.salvar(endereco)).thenReturn(endereco);

        Endereco enderecoSalvo = this.service.salvar(endereco);
        assertNotNull(enderecoSalvo);
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Endereco endereco = domainMock.getEndereco();

        when(service.editar(id, endereco)).thenReturn(endereco);

        Endereco enderecoAlterado = this.service.editar(id, endereco);
        assertNotNull(enderecoAlterado);
    }

}