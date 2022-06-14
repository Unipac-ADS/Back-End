package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.AplicacaoAlunoVagaResources;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Curso;
import br.com.stagiun.tccstagiun.model.domain.AplicacaoAlunoVaga;
import br.com.stagiun.tccstagiun.model.service.AplicacaoAlunoVagaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AplicacaoAlunoVagaResources.class)
//@Profile("Test")
@Slf4j
public class AplicacaoAlunoVagasResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AplicacaoAlunoVagaService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String aplicacaoAlunoVagaJson = getAplicacaoAlunoVagaJson();

        AplicacaoAlunoVaga aplicacaoAlunoVaga = domainMock.getAplicacaoAlunoVaga();
        when(service.findById(id)).thenReturn(Optional.of(aplicacaoAlunoVaga));
        mockMvc.perform(get("/v1/aplicacao-aluno-vagas/{id}", id))
                .andDo(print())
                .andExpect(content().json(aplicacaoAlunoVagaJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/aplicacao-aluno-vagas/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_aplicacaoAlunoVaga() throws Exception {
        List<AplicacaoAlunoVaga> aplicacaoAlunoVagaList = new ArrayList<>();

        AplicacaoAlunoVaga aplicacaoAlunoVaga = domainMock.getAplicacaoAlunoVaga();
        aplicacaoAlunoVagaList.add(aplicacaoAlunoVaga);

        when(service.list()).thenReturn(aplicacaoAlunoVagaList);
        mockMvc.perform(get("/v1/aplicacao-aluno-vagas"))
                .andDo(print())
                .andExpect(content().json("[{\"id\":1,\"dataAplicacao\":\"2021:09:01\",\"vaga\":{\"id\":1,\"amount\":333.0,\"dataOfertaInicio\":\"2001-01-01 12:45:09\",\"dataOfertaFim\":\"2001-01-01 12:45:09\",\"nome\":\"dfasdfasdf2\",\"cargo\":{\"id\":1,\"descricao\":\"Unipac\",\"experiencia\":\"Junior3223\",\"areaAtuacao\":\"mpmpmp\",\"beneficios\":\"nao tem\",\"salario\":2999.0,\"competenciasDesejadas\":\"Nenhuma\"},\"empresa\":{\"id\":1,\"tipoEmpresa\":{\"id\":1,\"descricao\":\"Tecnologia\"},\"usuario\":{\"id\":1,\"nome\":\"root\",\"email\":\"root@localhos.com\",\"senha\":\"123456\"},\"endereco\":{\"id\":1,\"tipo\":\"Av\",\"rua\":\"Afonso Pena\",\"numero\":\"323\",\"cep\":{\"id\":1,\"descricao\":\"Uberlandia\"}},\"nomeFantasia\":\"Empresa X\",\"razaoSocial\":\"Empresa X 23\",\"cnpj\":341423,\"telefone\":3242343,\"email\":\"rogerio@fon.com\"}},\"aluno\":{\"nome\":\"Brasil\"}}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        AplicacaoAlunoVaga aplicacaoAlunoVaga = domainMock.getAplicacaoAlunoVaga();
        String aplicacaoAlunoVagaJson = getAplicacaoAlunoVagaJson();

        when(this.service.salvar(aplicacaoAlunoVaga)).thenReturn(aplicacaoAlunoVaga);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/aplicacao-aluno-vagas").accept(MediaType.APPLICATION_JSON).content(aplicacaoAlunoVagaJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        AplicacaoAlunoVaga aplicacaoAlunoVaga = domainMock.getAplicacaoAlunoVaga();
        String aplicacaoAlunoVagaJson = getAplicacaoAlunoVagaJson();

        when(service.editar(id, aplicacaoAlunoVaga)).thenReturn(aplicacaoAlunoVaga);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/aplicacao-aluno-vagas/{id}", id).accept(MediaType.APPLICATION_JSON).content(aplicacaoAlunoVagaJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getAplicacaoAlunoVagaJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(domainMock.getAplicacaoAlunoVaga());
    }

}

