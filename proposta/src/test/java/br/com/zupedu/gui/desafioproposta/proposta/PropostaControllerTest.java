package br.com.zupedu.gui.desafioproposta.proposta;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class PropostaControllerTest {

    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PropostaRepository repository;

    @Test
    void deveCadastrarUmaNovaProposta() throws Exception {
        //arrange
        NovaPropostaRequest propostaRequest = new NovaPropostaRequest("516.303.020-58","email@teste.com",
                "Joao Teste","Rua Teste n 123",new BigDecimal("2000.00"));
        String request = mapper.writeValueAsString(propostaRequest);
        String URI = "/propostas";
        //act
        MockHttpServletRequestBuilder consultaRequest = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON).content(request);
        //assert
        mockMvc.perform(consultaRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(
                        MockMvcResultMatchers.status().isCreated()
                );
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null,null,null,null","'' ,'','',''",},nullValues = {"null"},emptyValue = "")
    void deveRetornarBadRequestCasoTenhaDadosNullosOuEmBranco(String cpf,String email,String nome,String endereco) throws Exception {
        NovaPropostaRequest propostaRequest = new NovaPropostaRequest(cpf,email,
                nome,endereco,null);
        String request = mapper.writeValueAsString(propostaRequest);
        String URI = "/propostas";
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
        String URI = "/propostas";
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
        String URI = "/propostas";
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
        String URI = "/propostas";
        MockHttpServletRequestBuilder consultaRequest = post(URI).contentType(MediaType.APPLICATION_JSON)
                .content(request);
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(
                        status().isUnprocessableEntity()
                );

    }
}
