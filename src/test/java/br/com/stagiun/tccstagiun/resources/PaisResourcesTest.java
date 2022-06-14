package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.PaisResources;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Pais;
import br.com.stagiun.tccstagiun.model.domain.Pais;
import br.com.stagiun.tccstagiun.model.service.PaisService;
import br.com.stagiun.tccstagiun.model.service.PaisService;
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
@WebMvcTest(PaisResources.class)
//@Profile("Test")
@Slf4j
public class PaisResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaisService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String paisJson = getPaisJson();

        Pais pais = domainMock.getPais();
        when(service.findById(id)).thenReturn(Optional.of(pais));
        mockMvc.perform(get("/v1/paises/{id}", id))
                .andDo(print())
                .andExpect(content().json(paisJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/paises/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_paises() throws Exception {
        List<Pais> paisList = new ArrayList<>();

        Pais pais = domainMock.getPais();
        paisList.add(pais);

        Pais pais2 = domainMock.getPais2();
        paisList.add(pais2);

        Pais pais3 = domainMock.getPais3();
        paisList.add(pais3);

        when(service.list()).thenReturn(paisList);
        mockMvc.perform(get("/v1/paises"))
                .andDo(print())
                .andExpect(content().json("[{\"descricao\":\"Brasil\"},{\"descricao\":\"USA\"},{\"descricao\":\"Mexico\"}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Pais pais = domainMock.getPais();
        String paisJson = getPaisJson();

        when(this.service.salvar(pais)).thenReturn(pais);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/paises").accept(MediaType.APPLICATION_JSON).content(paisJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Pais pais = domainMock.getPais();
        String paisJson = getPaisJson();

        when(service.editar(id, pais)).thenReturn(pais);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/paises/{id}", id).accept(MediaType.APPLICATION_JSON).content(paisJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getPaisJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(domainMock.getPais());
    }

}

