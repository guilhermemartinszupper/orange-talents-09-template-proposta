package br.com.zupedu.gui.desafioproposta.proposta;

import br.com.zupedu.gui.desafioproposta.proposta.analise.StatusProposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
@ActiveProfiles(profiles = "test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class PropostaControllerTest {

    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PropostaRepository repository;
    String URI = "/propostas";

    @Test
    void deveCadastrarUmaNovaProposta() throws Exception {
        //arrange
        NovaPropostaRequest propostaRequest = new NovaPropostaRequest("516.303.020-58","email@teste.com",
                "Joao Teste","Rua Teste n 123",new BigDecimal("2000.00"));
        String request = mapper.writeValueAsString(propostaRequest);
        //act
        MockHttpServletRequestBuilder consultaRequest = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON).content(request);
        //assert
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("**/propostas/{id}"));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null,null,null,null","'' ,'','',''",},nullValues = {"null"},emptyValue = "")
    void deveRetornarBadRequestCasoTenhaDadosNullosOuEmBranco(String cpf,String email,String nome,String endereco) throws Exception {
        NovaPropostaRequest propostaRequest = new NovaPropostaRequest(cpf,email,
                nome,endereco,null);
        String request = mapper.writeValueAsString(propostaRequest);
        MockHttpServletRequestBuilder consultaRequest = post(URI).contentType(MediaType.APPLICATION_JSON)
                .content(request);
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(
                        status().isBadRequest()
                );
    }
    @Test
    void deveRetornarBadRequestCasoCpfSejaInvalido() throws Exception {
        NovaPropostaRequest propostaRequest = new NovaPropostaRequest("123.456.789-58","email@teste.com",
                "Joao Teste","Rua Teste n 123",new BigDecimal("2000.00"));
        String request = mapper.writeValueAsString(propostaRequest);
        MockHttpServletRequestBuilder consultaRequest = post(URI).contentType(MediaType.APPLICATION_JSON)
                .content(request);
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(
                        status().isBadRequest()
                );
    }
    @Test
    void deveRetornarBadRequestCasoEmailSejaInvalido() throws Exception {
        NovaPropostaRequest propostaRequest = new NovaPropostaRequest("516.303.020-58","emailteste.com",
                "Joao Teste","Rua Teste n 123",new BigDecimal("2000.00"));
        String request = mapper.writeValueAsString(propostaRequest);
        MockHttpServletRequestBuilder consultaRequest = post(URI).contentType(MediaType.APPLICATION_JSON)
                .content(request);
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(
                        status().isBadRequest()
                );
    }
    @Test
    void deveRetornar422CasoCPFSejaRepetido() throws Exception {
        Proposta proposta1 = new Proposta("516.303.020-58","email@teste.com",
                "Joao Teste","Rua Teste n 123",new BigDecimal("2000.00"));
        repository.save(proposta1);
        NovaPropostaRequest proposta2 = new NovaPropostaRequest("516.303.020-58","email2@teste.com",
                "Maria Teste","Rua Teste n 56",new BigDecimal("2500.00"));
        String request = mapper.writeValueAsString(proposta2);
        MockHttpServletRequestBuilder consultaRequest = post(URI).contentType(MediaType.APPLICATION_JSON)
                .content(request);
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(
                        status().isUnprocessableEntity()
                );
    }
    @Test
    void deveRetornarBadRequestCasoSalarioSejaNegativo() throws Exception {
        NovaPropostaRequest propostaRequest = new NovaPropostaRequest("516.303.020-58","email@teste.com",
                "Joao Teste","Rua Teste n 123",new BigDecimal("-2000.00"));
        String request = mapper.writeValueAsString(propostaRequest);

        MockHttpServletRequestBuilder consultaRequest = post(URI).contentType(MediaType.APPLICATION_JSON)
                .content(request);
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(
                        status().isBadRequest()
                );
    }
    @Test
    void deveRetornarUmaProposta() throws Exception {
        Proposta proposta = new Proposta("516.303.020-58","email@teste.com",
                "Joao Teste","Rua Teste n 123",new BigDecimal("2000.00"));
        repository.save(proposta);
        MockHttpServletRequestBuilder consultaRequest = get(URI + "/" + proposta.getId());
        MvcResult result = mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Proposta proposta2 = mapper.readValue(result.getResponse().getContentAsString(),Proposta.class);
        Assertions.assertEquals(proposta.getId(),proposta2.getId());
    }
    @Test
    void deveBuscarUmaPropostaInexistenteERetornar404() throws Exception {
        MockHttpServletRequestBuilder consultaRequest = get(URI + "/ + 999");
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
