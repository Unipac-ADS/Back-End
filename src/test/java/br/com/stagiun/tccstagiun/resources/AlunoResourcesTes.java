package br.com.stagiun.tccstagiun.resources;

import br.com.stagiun.tccstagiun.controller.AlunoResources;
import br.com.stagiun.tccstagiun.model.domain.Aluno;
import br.com.stagiun.tccstagiun.model.service.AlunoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import util.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(AlunoResources.class)
@Profile("Test")
public class AlunoResourcesTes {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private AlunoService service;

        private Aluno getAluno() {
            return Aluno.builder()
                    .nome("Brasil")
                    .build();
        }

        @Test
        public void find_by_countries_and_thenStatus200_and_aluno() throws Exception {
            Long id = 1l;
            String response = JsonUtil.getJson(getAluno());

            Aluno aluno = getAluno();
            given(service.findById(id).get()).willReturn(aluno);

            mockMvc.perform(get("/api/v1/countries/{id}", id)).andDo(print()).andExpect(status().isOk());
            // .andExpect(content().string(response));
        }

        //@Test
        public void givenCountries_whenGetCountries_thenStatus201() throws Exception {

            Aluno url = getAluno();
            when(this.service.salvar(url)).thenReturn(url);

            RequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post("/api/v1/countries")
                    .accept(MediaType.APPLICATION_JSON).content(JsonUtil.getJson(getAluno()))
                    .contentType(MediaType.APPLICATION_JSON);

            MvcResult result = mockMvc.perform(requestBuilder).andReturn();

            MockHttpServletResponse response = result.getResponse();

            String content = response.getContentAsString();
            log.info(content);
            assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        }

    }