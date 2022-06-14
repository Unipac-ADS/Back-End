package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.UsuarioResources;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Usuario;
import br.com.stagiun.tccstagiun.model.service.UsuarioService;
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

import java.math.BigDecimal;
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
@WebMvcTest(UsuarioResources.class)
//@Profile("Test")
@Slf4j
public class UsuarioResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsuarioService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String usuarioJson = getUsuarioJson();

        Usuario usuario = domainMock.getUsuario();
        when(service.findById(id)).thenReturn(Optional.of(usuario));
        mockMvc.perform(get("/v1/usuarios/{id}", id))
                .andDo(print())
                .andExpect(content().json(usuarioJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/usuarios/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_usuario() throws Exception {
        List<Usuario> usuarioList = new ArrayList<>();

        Usuario usuario = domainMock.getUsuario();
        usuarioList.add(usuario);

        when(service.list()).thenReturn(usuarioList);
        mockMvc.perform(get("/v1/usuarios"))
                .andDo(print())
                .andExpect(content().json("[{\"id\":1,\"nome\":\"root\",\"email\":\"root@localhos.com\",\"senha\":\"123456\"}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Usuario usuario = domainMock.getUsuario();
        String usuarioJson = getUsuarioJson();

        when(this.service.salvar(usuario)).thenReturn(usuario);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/usuarios").accept(MediaType.APPLICATION_JSON).content(usuarioJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Usuario usuario = domainMock.getUsuario();
        String usuarioJson = getUsuarioJson();

        when(service.editar(id, usuario)).thenReturn(usuario);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/usuarios/{id}", id).accept(MediaType.APPLICATION_JSON).content(usuarioJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getUsuarioJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(domainMock.getUsuario());
    }

}

