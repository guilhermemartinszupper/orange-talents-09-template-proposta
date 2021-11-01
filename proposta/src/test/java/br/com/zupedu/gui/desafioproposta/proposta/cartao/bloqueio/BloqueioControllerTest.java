package br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio;

import br.com.zupedu.gui.desafioproposta.proposta.Proposta;
import br.com.zupedu.gui.desafioproposta.proposta.PropostaRepository;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
@ActiveProfiles(profiles = "test")
class BloqueioControllerTest {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PropostaRepository propostaRepository;

    String URI = "/cartoes/bloqueios";

    @BeforeEach()
    void setUp(){
        Proposta proposta = new Proposta("516.303.020-58","email@teste.com",
                "Joao Teste","Rua Teste n 123",new BigDecimal("2000.00"));
        Cartao cartao = new Cartao("1111-2222-3333-4444", LocalDateTime.now(), "Dono Cartao", 1000);
        proposta.setCartao(cartao);
        propostaRepository.save(proposta);
        URI += "/" + proposta.getCartao().getId();
    }

    @Test
    void deveRealizarUmBloqueioParaUmCartaoExistente() throws Exception {
        MockHttpServletRequestBuilder consultaRequest = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent","PostmanRuntime/7.28.4");
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void deveRetornar400CasoNaoTenhaUserAgent() throws Exception {
        MockHttpServletRequestBuilder consultaRequest = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    void deveRetornar404CasoCartaoNaoExista() throws Exception {
        MockHttpServletRequestBuilder consultaRequest = MockMvcRequestBuilders.post(URI + "404").contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent","PostmanRuntime/7.28.4");
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    void deveRetornar422CasoJaExistaUmBloqueioParaOCartao() throws Exception {
        MockHttpServletRequestBuilder consultaRequest = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent","PostmanRuntime/7.28.4");
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity() );
    }
}