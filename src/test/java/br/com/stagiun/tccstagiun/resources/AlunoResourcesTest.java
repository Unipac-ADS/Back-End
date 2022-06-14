package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.AlunoResources;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Aluno;
import br.com.stagiun.tccstagiun.model.service.AlunoService;
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
@WebMvcTest(AlunoResources.class)
//@Profile("Test")
@Slf4j
public class AlunoResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AlunoService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    public void find_by_student_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String alunoJson = getAlunoJson();

        Aluno aluno = domainMock.getAluno();
        when(service.findById(id)).thenReturn(Optional.of(aluno));
        mockMvc.perform(get("/v1/alunos/{id}", id))
                .andDo(print())
                .andExpect(content().json(alunoJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/alunos/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_aluno() throws Exception {
        List<Aluno> alunoList = new ArrayList<>();

        Aluno aluno = domainMock.getAluno();
        alunoList.add(aluno);

        Aluno aluno2 = domainMock.getAluno2();
        alunoList.add(aluno2);

        Aluno aluno3 = domainMock.getAluno3();
        alunoList.add(aluno3);

        when(service.list()).thenReturn(alunoList);
        mockMvc.perform(get("/v1/alunos"))
                .andDo(print())
                .andExpect(content().json("[{\"nome\":\"Brasil\"},{\"nome\":\"Antonio\"},{\"nome\":\"Lua\"}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Aluno aluno = domainMock.getAluno();
        String alunoJson = getAlunoJson();

        when(this.service.salvar(aluno)).thenReturn(aluno);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/alunos").accept(MediaType.APPLICATION_JSON).content(alunoJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Aluno aluno = domainMock.getAluno();
        String alunoJson = getAlunoJson();

        when(service.editar(id, aluno)).thenReturn(aluno);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/alunos/{id}", id).accept(MediaType.APPLICATION_JSON).content(alunoJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getAlunoJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(domainMock.getAluno());
    }

}

