package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.DicaResources;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Dica;
import br.com.stagiun.tccstagiun.model.service.DicaService;
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
@WebMvcTest(DicaResources.class)
//@Profile("Test")
@Slf4j
public class DicaResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DicaService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String tipoDicaJson = getDicaJson();

        Dica tipoDica = domainMock.getDica();
        when(service.findById(id)).thenReturn(Optional.of(tipoDica));
        mockMvc.perform(get("/v1/dicas/{id}", id))
                .andDo(print())
                .andExpect(content().json(tipoDicaJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/dicas/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_tipoDica() throws Exception {
        List<Dica> tipoDicaList = new ArrayList<>();

        Dica tipoDica = domainMock.getDica();
        tipoDicaList.add(tipoDica);

        when(service.list()).thenReturn(tipoDicaList);
        mockMvc.perform(get("/v1/dicas"))
                .andDo(print())
                .andExpect(content().json("[{\"id\":1,\"titulo\":\"dica 1\",\"descricao\":\"dica 1\",\"linksUteis\":\"links udeis\",\"dataPublicacao\":\"2001-01-01\",\"cargo\":{\"id\":1,\"descricao\":\"Unipac\",\"experiencia\":\"Junior3223\",\"areaAtuacao\":\"mpmpmp\",\"beneficios\":\"nao tem\",\"salario\":2999.0,\"competenciasDesejadas\":\"Nenhuma\"}}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Dica tipoDica = domainMock.getDica();
        String tipoDicaJson = getDicaJson();

        when(this.service.salvar(tipoDica)).thenReturn(tipoDica);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/dicas").accept(MediaType.APPLICATION_JSON).content(tipoDicaJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Dica tipoDica = domainMock.getDica();
        String tipoDicaJson = getDicaJson();

        when(service.editar(id, tipoDica)).thenReturn(tipoDica);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/dicas/{id}", id).accept(MediaType.APPLICATION_JSON).content(tipoDicaJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getDicaJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(domainMock.getDica());
    }

}

