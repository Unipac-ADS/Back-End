package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.CidadeResources;
import br.com.stagiun.tccstagiun.model.domain.Cidade;
import br.com.stagiun.tccstagiun.model.domain.Estado;
import br.com.stagiun.tccstagiun.model.domain.Pais;
import br.com.stagiun.tccstagiun.model.service.CidadeService;
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
@WebMvcTest(CidadeResources.class)
//@Profile("Test")
@Slf4j
public class CidadeResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CidadeService service;

    private Estado getEstado() {
        return Estado.builder().id(1L).descricao
                ("Minas Gerais").build();
    }

    private Cidade getCidade() {
        return Cidade.builder().descricao("Ubelandia").estado(getEstado()).build();
    }

    private Cidade getCidade2() {
        return Cidade.builder().descricao("Uberaba").estado(getEstado()).build();
    }

    private Cidade getCidade3() {
        return Cidade.builder().descricao("Araguari").estado(getEstado()).build();
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String cidadeJson = getCidadeJson();

        Cidade cidade = getCidade();
        when(service.findById(id)).thenReturn(Optional.of(cidade));
        mockMvc.perform(get("/v1/cidades/{id}", id))
                .andDo(print())
                .andExpect(content().json(cidadeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/cidades/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_cidade() throws Exception {
        List<Cidade> cidadeList = new ArrayList<>();

        Cidade cidade = getCidade();
        cidadeList.add(cidade);

        Cidade cidade2 = getCidade2();
        cidadeList.add(cidade2);

        Cidade cidade3 = getCidade3();
        cidadeList.add(cidade3);

        when(service.list()).thenReturn(cidadeList);
        mockMvc.perform(get("/v1/cidades"))
                .andDo(print())
                .andExpect(content().json("[{\"descricao\":\"Ubelandia\",\"estado\":{\"id\":1,\"descricao\":\"Minas Gerais\"}},{\"descricao\":\"Uberaba\",\"estado\":{\"id\":1,\"descricao\":\"Minas Gerais\"}},{\"descricao\":\"Araguari\",\"estado\":{\"id\":1,\"descricao\":\"Minas Gerais\"}}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Cidade cidade = getCidade();
        String cidadeJson = getCidadeJson();

        when(this.service.salvar(cidade)).thenReturn(cidade);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/cidades").accept(MediaType.APPLICATION_JSON).content(cidadeJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Cidade cidade = getCidade();
        String cidadeJson = getCidadeJson();

        when(service.editar(id, cidade)).thenReturn(cidade);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/cidades/{id}", id).accept(MediaType.APPLICATION_JSON).content(cidadeJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getCidadeJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(getCidade());
    }

}

