package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.FaculdadeResources;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Faculdade;
import br.com.stagiun.tccstagiun.model.service.FaculdadeService;
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
@WebMvcTest(FaculdadeResources.class)
//@Profile("Test")
@Slf4j
public class FaculdadeResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FaculdadeService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String faculdadeJson = getFaculdadeJson();

        Faculdade faculdade = domainMock.getFaculdade();
        when(service.findById(id)).thenReturn(Optional.of(faculdade));
        mockMvc.perform(get("/v1/faculdades/{id}", id))
                .andDo(print())
                .andExpect(content().json(faculdadeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/faculdades/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_faculdade() throws Exception {
        List<Faculdade> faculdadeList = new ArrayList<>();

        Faculdade faculdade = domainMock.getFaculdade();
        faculdadeList.add(faculdade);

        when(service.list()).thenReturn(faculdadeList);
        mockMvc.perform(get("/v1/faculdades"))
                .andDo(print())
                .andExpect(content().json("[{\"id\":1,\"nome\":\"Unipac\"}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Faculdade faculdade = domainMock.getFaculdade();
        String faculdadeJson = getFaculdadeJson();

        when(this.service.salvar(faculdade)).thenReturn(faculdade);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/faculdades").accept(MediaType.APPLICATION_JSON).content(faculdadeJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Faculdade faculdade = domainMock.getFaculdade();
        String faculdadeJson = getFaculdadeJson();

        when(service.editar(id, faculdade)).thenReturn(faculdade);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/faculdades/{id}", id).accept(MediaType.APPLICATION_JSON).content(faculdadeJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getFaculdadeJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(domainMock.getFaculdade());
    }

}

