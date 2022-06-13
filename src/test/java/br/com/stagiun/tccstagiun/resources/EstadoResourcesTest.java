package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.EstadoResources;
import br.com.stagiun.tccstagiun.model.domain.Estado;
import br.com.stagiun.tccstagiun.model.domain.Pais;
import br.com.stagiun.tccstagiun.model.service.EstadoService;
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
@WebMvcTest(EstadoResources.class)
//@Profile("Test")
@Slf4j
public class EstadoResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EstadoService service;

    private Pais getPais() {
        return Pais.builder().id(1L).descricao
                ("Brasil").build();
    }

    private Estado getEstado() {
        return Estado.builder().descricao("Minas Gerais").pais(getPais()).build();
    }

    private Estado getEstado2() {
        return Estado.builder().descricao("São Paulo").pais(getPais()).build();
    }

    private Estado getEstado3() {
        return Estado.builder().pais(getPais()).descricao("Parana").build();
    }

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String estadoJson = getEstadoJson();

        Estado estado = getEstado();
        when(service.findById(id)).thenReturn(Optional.of(estado));
        mockMvc.perform(get("/v1/estados/{id}", id))
                .andDo(print())
                .andExpect(content().json(estadoJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/estados/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_estado() throws Exception {
        List<Estado> estadoList = new ArrayList<>();

        Estado estado = getEstado();
        estadoList.add(estado);

        Estado estado2 = getEstado2();
        estadoList.add(estado2);

        Estado estado3 = getEstado3();
        estadoList.add(estado3);

        when(service.list()).thenReturn(estadoList);
        mockMvc.perform(get("/v1/estados"))
                .andDo(print())
                .andExpect(content().json("[{\"descricao\":\"Minas Gerais\",\"pais\":{\"id\":1,\"descricao\":\"Brasil\"}},{\"descricao\":\"São Paulo\",\"pais\":{\"id\":1,\"descricao\":\"Brasil\"}},{\"descricao\":\"Parana\",\"pais\":{\"id\":1,\"descricao\":\"Brasil\"}}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Estado estado = getEstado();
        String estadoJson = getEstadoJson();

        when(this.service.salvar(estado)).thenReturn(estado);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/estados").accept(MediaType.APPLICATION_JSON).content(estadoJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Estado estado = getEstado();
        String estadoJson = getEstadoJson();

        when(service.editar(id, estado)).thenReturn(estado);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/estados/{id}", id).accept(MediaType.APPLICATION_JSON).content(estadoJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getEstadoJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(getEstado());
    }

}

