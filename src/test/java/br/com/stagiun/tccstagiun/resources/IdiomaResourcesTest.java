package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.IdiomaResources;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Idioma;
import br.com.stagiun.tccstagiun.model.service.IdiomaService;
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
@WebMvcTest(IdiomaResources.class)
//@Profile("Test")
@Slf4j
public class IdiomaResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IdiomaService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String tipoIdiomaJson = getIdiomaJson();

        Idioma tipoIdioma = domainMock.getIdioma();
        when(service.findById(id)).thenReturn(Optional.of(tipoIdioma));
        mockMvc.perform(get("/v1/idiomas/{id}", id))
                .andDo(print())
                .andExpect(content().json(tipoIdiomaJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/idiomas/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_tipoIdioma() throws Exception {
        List<Idioma> tipoIdiomaList = new ArrayList<>();

        Idioma tipoIdioma = domainMock.getIdioma();
        tipoIdiomaList.add(tipoIdioma);

        when(service.list()).thenReturn(tipoIdiomaList);
        mockMvc.perform(get("/v1/idiomas"))
                .andDo(print())
                .andExpect(content().json("[{\"id\":1,\"nome\":\"Inglês\",\"aluno\":{\"nome\":\"Maria\",\"matricula\":\"ADS001\",\"cpf\":1245683215,\"telefone\":32564748,\"email\":\"brteste@hotmail.com\",\"curriculo\":\"cvteste1\"}}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Idioma tipoIdioma = domainMock.getIdioma();
        String tipoIdiomaJson = getIdiomaJson();

        when(this.service.salvar(tipoIdioma)).thenReturn(tipoIdioma);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/idiomas").accept(MediaType.APPLICATION_JSON).content(tipoIdiomaJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Idioma tipoIdioma = domainMock.getIdioma();
        String tipoIdiomaJson = getIdiomaJson();

        when(service.editar(id, tipoIdioma)).thenReturn(tipoIdioma);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/idiomas/{id}", id).accept(MediaType.APPLICATION_JSON).content(tipoIdiomaJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getIdiomaJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(domainMock.getIdioma());
    }

}

