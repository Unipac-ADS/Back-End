package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.CargoResources;
import br.com.stagiun.tccstagiun.mocks.DomainMockFactory;
import br.com.stagiun.tccstagiun.model.domain.Cargo;
import br.com.stagiun.tccstagiun.model.service.CargoService;
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
@WebMvcTest(CargoResources.class)
//@Profile("Test")
@Slf4j
public class CargoResourcesTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CargoService service;

    private DomainMockFactory domainMock = DomainMockFactory.getDomainMockFactory();

    @Test
    public void find_by_countries_by_id_and_thenStatus200() throws Exception {
        Long id = 1L;
        String cargoJson = getCargoJson();

        Cargo cargo = domainMock.getCargo();
        when(service.findById(id)).thenReturn(Optional.of(cargo));
        mockMvc.perform(get("/v1/cargos/{id}", id))
                .andDo(print())
                .andExpect(content().json(cargoJson))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_countries_and_thenStatus204() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/v1/cargos/{id}", id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void find_by_countries_and_thenStatus200_and_all_cargo() throws Exception {
        List<Cargo> cargoList = new ArrayList<>();

        Cargo cargo = domainMock.getCargo();
        cargoList.add(cargo);

        when(service.list()).thenReturn(cargoList);
        mockMvc.perform(get("/v1/cargos"))
                .andDo(print())
                .andExpect(content().json("[{\"id\":1,\"descricao\":\"Dev Front-End\",\"experiencia\":\"Júnior\",\"areaAtuacao\":\"Web\",\"beneficios\":\"Tudo\",\"salario\":2999.00,\"habilidadesDesejadas\":\"Inglês\",\"competenciasDesejadas\":\"HTML/CSS, JavaScript, React/Angular\"}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStudents_whenSaveStudent_thenStatus201() throws Exception {
        Cargo cargo = domainMock.getCargo();
        String cargoJson = getCargoJson();

        when(this.service.salvar(cargo)).thenReturn(cargo);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/cargos").accept(MediaType.APPLICATION_JSON).content(cargoJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void givenStudents_whenUpdateStudent_thenStatus200() throws Exception {
        Long id = 1L;
        Cargo cargo = domainMock.getCargo();
        String cargoJson = getCargoJson();

        when(service.editar(id, cargo)).thenReturn(cargo);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/cargos/{id}", id).accept(MediaType.APPLICATION_JSON).content(cargoJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info(content);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private String getCargoJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(domainMock.getCargo());
    }

}

