package br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio;

import br.com.zupedu.gui.desafioproposta.proposta.Proposta;
import br.com.zupedu.gui.desafioproposta.proposta.PropostaRepository;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.CartaoRepository;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.ContaClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
@ActiveProfiles(profiles = "test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class BloqueioControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PropostaRepository propostaRepository;
    @MockBean
    ContaClient contaClient;
    @Autowired
    CartaoRepository cartaoRepository;
    Long idCartao;
    String Uri = "/cartoes";

    @BeforeEach()
    void setUp(){
        Proposta proposta = new Proposta("516.303.020-58","email@teste.com",
                "Joao Teste","Rua Teste n 123",new BigDecimal("2000.00"));
        Cartao cartao = new Cartao("1111-2222-3333-4444", LocalDateTime.now(), "Dono Cartao", 1000);
        proposta.setCartao(cartao);
        propostaRepository.save(proposta);
        idCartao = proposta.getCartao().getId();
        Uri += "/" + idCartao + "/bloqueios";
    }

    @Test
    void deveRealizarUmBloqueioParaUmCartaoExistente() throws Exception {
        MockHttpServletRequestBuilder consultaRequest = MockMvcRequestBuilders.post(Uri).contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent","PostmanRuntime/7.28.4");
        Mockito.when(contaClient.solicitarBloqueio(Mockito.any(),Mockito.any())).thenReturn(new SolicitacaoBloqueioResponse(ResultadoBloqueio.BLOQUEADO));
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void deveRetornar400CasoNaoTenhaUserAgent() throws Exception {
        MockHttpServletRequestBuilder consultaRequest = MockMvcRequestBuilders.post(Uri).contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    void deveRetornar404CasoCartaoNaoExista() throws Exception {
        MockHttpServletRequestBuilder consultaRequest = MockMvcRequestBuilders.post(Uri + "404").contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent","PostmanRuntime/7.28.4");
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    @Transactional
    void deveRetornar422CasoJaExistaUmBloqueioParaOCartao() throws Exception {
        MockHttpServletRequestBuilder consultaRequest = MockMvcRequestBuilders.post(Uri).contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent","PostmanRuntime/7.28.4");
        Optional<Cartao> cartao = cartaoRepository.findById(idCartao);
        cartao.get().bloquear();
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity() );
    }
}