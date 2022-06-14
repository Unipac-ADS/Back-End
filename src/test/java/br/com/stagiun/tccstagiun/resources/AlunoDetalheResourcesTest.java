package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.AlunoDetalheResources;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhe;
import br.com.stagiun.tccstagiun.model.service.AlunoDetalheService;
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
@WebMvcTest(AlunoDetalheResources.class)
//@Profile("Test")
@Slf4j
public class AlunoDetalheResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AlunoDetalheService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    public void find_by_student_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String alunoJson = getAlunoDetalheJson();

        AlunoDetalhe alunoDetalhes = domainMock.getAlunoDetalhe();
        when(service.findById(id)).thenReturn(Optional.of(alunoDetalhes));
        mockMvc.perform(get("/v1/aluno-detalhes/{id}", id))
                .andDo(print())
                .andExpect(content().json(alunoJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/aluno-detalhes/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_aluno() throws Exception {
        List<AlunoDetalhe> alunoList = new ArrayList<>();

        AlunoDetalhe aluno = domainMock.getAlunoDetalhe();
        alunoList.add(aluno);

        AlunoDetalhe aluno2 = domainMock.getAlunoDetalhe();
        alunoList.add(aluno2);

        AlunoDetalhe aluno3 = domainMock.getAlunoDetalhe();
        alunoList.add(aluno3);

        when(service.list()).thenReturn(alunoList);
        mockMvc.perform(get("/v1/aluno-detalhes"))
                .andDo(print())
                .andExpect(content().json("[{\"id\":1,\"aluno\":{\"nome\":\"Brasil\"},\"turma\":{\"id\":1,\"descricao\":\"3B\",\"periodo\":1,\"curso\":{\"id\":1,\"descricao\":\"Analise de Sistema\",\"faculdade\":{\"id\":1,\"nome\":\"Unipac\"}}},\"anoDeInicioCurso\":\"2001\",\"anoDeConclusaoCurso\":\"2002\",\"experiencia\":\"experiencia\",\"infoAdicionais\":\"info adincionais\",\"deficiencia\":1,\"sobre\":\"Sobre\",\"linkedin\":\"linkedin_user\",\"github\":\"github_user\",\"instagram\":\"instagram_user\",\"twitter\":\"twitter_user\",\"facebook\":\"facebook_user\",\"fileCurriculo\":\"fdsafsd\"},{\"id\":1,\"aluno\":{\"nome\":\"Brasil\"},\"turma\":{\"id\":1,\"descricao\":\"3B\",\"periodo\":1,\"curso\":{\"id\":1,\"descricao\":\"Analise de Sistema\",\"faculdade\":{\"id\":1,\"nome\":\"Unipac\"}}},\"anoDeInicioCurso\":\"2001\",\"anoDeConclusaoCurso\":\"2002\",\"experiencia\":\"experiencia\",\"infoAdicionais\":\"info adincionais\",\"deficiencia\":1,\"sobre\":\"Sobre\",\"linkedin\":\"linkedin_user\",\"github\":\"github_user\",\"instagram\":\"instagram_user\",\"twitter\":\"twitter_user\",\"facebook\":\"facebook_user\",\"fileCurriculo\":\"fdsafsd\"},{\"id\":1,\"aluno\":{\"nome\":\"Brasil\"},\"turma\":{\"id\":1,\"descricao\":\"3B\",\"periodo\":1,\"curso\":{\"id\":1,\"descricao\":\"Analise de Sistema\",\"faculdade\":{\"id\":1,\"nome\":\"Unipac\"}}},\"anoDeInicioCurso\":\"2001\",\"anoDeConclusaoCurso\":\"2002\",\"experiencia\":\"experiencia\",\"infoAdicionais\":\"info adincionais\",\"deficiencia\":1,\"sobre\":\"Sobre\",\"linkedin\":\"linkedin_user\",\"github\":\"github_user\",\"instagram\":\"instagram_user\",\"twitter\":\"twitter_user\",\"facebook\":\"facebook_user\",\"fileCurriculo\":\"fdsafsd\"}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        AlunoDetalhe aluno = domainMock.getAlunoDetalhe();
        String alunoJson = getAlunoDetalheJson();

        when(this.service.salvar(aluno)).thenReturn(aluno);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/aluno-detalhes").accept(MediaType.APPLICATION_JSON).content(alunoJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        AlunoDetalhe aluno = domainMock.getAlunoDetalhe();
        String alunoJson = getAlunoDetalheJson();

        when(service.editar(id, aluno)).thenReturn(aluno);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/aluno-detalhes/{id}", id).accept(MediaType.APPLICATION_JSON).content(alunoJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getAlunoDetalheJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(domainMock.getAlunoDetalhe());
    }

}

