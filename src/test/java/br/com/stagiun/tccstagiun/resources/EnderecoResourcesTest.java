package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.EnderecoResources;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Cep;
import br.com.stagiun.tccstagiun.model.domain.Endereco;
import br.com.stagiun.tccstagiun.model.domain.Cidade;
import br.com.stagiun.tccstagiun.model.service.EnderecoService;
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
@WebMvcTest(EnderecoResources.class)
//@Profile("Test")
@Slf4j
public class EnderecoResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EnderecoService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String enderecoJson = getEnderecoJson();

        Endereco endereco = domainMock.getEndereco();
        when(service.findById(id)).thenReturn(Optional.of(endereco));
        mockMvc.perform(get("/v1/enderecos/{id}", id))
                .andDo(print())
                .andExpect(content().json(enderecoJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/enderecos/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_endereco() throws Exception {
        List<Endereco> enderecoList = new ArrayList<>();

        Endereco endereco = domainMock.getEndereco();
        enderecoList.add(endereco);

        when(service.list()).thenReturn(enderecoList);
        mockMvc.perform(get("/v1/enderecos"))
                .andDo(print())
                .andExpect(content().json("[{\"id\":1,\"tipo\":\"Av\",\"rua\":\"Afonso Pena\",\"numero\":\"323\",\"cep\":{\"id\":1,\"descricao\":\"Uberlandia\"}}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Endereco endereco = domainMock.getEndereco();
        String enderecoJson = getEnderecoJson();

        when(this.service.salvar(endereco)).thenReturn(endereco);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/enderecos").accept(MediaType.APPLICATION_JSON).content(enderecoJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Endereco endereco = domainMock.getEndereco();
        String enderecoJson = getEnderecoJson();

        when(service.editar(id, endereco)).thenReturn(endereco);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/enderecos/{id}", id).accept(MediaType.APPLICATION_JSON).content(enderecoJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getEnderecoJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(domainMock.getEndereco());
    }

}

