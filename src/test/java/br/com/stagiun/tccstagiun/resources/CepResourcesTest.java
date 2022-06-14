package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.BairroResources;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Bairro;
import br.com.stagiun.tccstagiun.model.domain.Cep;
import br.com.stagiun.tccstagiun.model.domain.Cidade;
import br.com.stagiun.tccstagiun.model.service.BairroService;
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
@WebMvcTest(BairroResources.class)
//@Profile("Test")
@Slf4j
public class CepResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BairroService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String bairroJson = getBairroJson();

        Bairro bairro = domainMock.getBairro();
        when(service.findById(id)).thenReturn(Optional.of(bairro));
        mockMvc.perform(get("/v1/bairros/{id}", id))
                .andDo(print())
                .andExpect(content().json(bairroJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/bairros/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_bairro() throws Exception {
        List<Bairro> bairroList = new ArrayList<>();

        Bairro bairro = domainMock.getBairro();
        bairroList.add(bairro);

        Bairro bairro2 = domainMock.getBairro2();
        bairroList.add(bairro2);

        when(service.list()).thenReturn(bairroList);
        mockMvc.perform(get("/v1/bairros"))
                .andDo(print())
                .andExpect(content().json("[{\"descricao\":\"Centro\",\"cidade\":{\"id\":1,\"descricao\":\"Uberlandia\"}},{\"descricao\":\"Centro\",\"cidade\":{\"id\":2,\"descricao\":\"Uberaba\"}}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Bairro bairro = domainMock.getBairro();
        String bairroJson = getBairroJson();

        when(this.service.salvar(bairro)).thenReturn(bairro);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/bairros").accept(MediaType.APPLICATION_JSON).content(bairroJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Bairro bairro = domainMock.getBairro();
        String bairroJson = getBairroJson();

        when(service.editar(id, bairro)).thenReturn(bairro);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/bairros/{id}", id).accept(MediaType.APPLICATION_JSON).content(bairroJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getBairroJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(domainMock.getBairro());
    }

}

