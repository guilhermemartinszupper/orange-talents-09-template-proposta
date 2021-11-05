package br.com.zupedu.gui.desafioproposta.proposta.cartao.carteiraDigital;

import br.com.zupedu.gui.desafioproposta.proposta.Proposta;
import br.com.zupedu.gui.desafioproposta.proposta.PropostaRepository;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.CartaoRepository;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.ContaClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
@ActiveProfiles(profiles = "test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class CarteiraDigitalControllerTest {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PropostaRepository propostaRepository;
    @Autowired
    CartaoRepository cartaoRepository;
    @MockBean
    ContaClient contaClient;

    String Uri = "/cartoes";
    Long idCartao;

    @BeforeEach()
    void setUp(){
        Proposta proposta = new Proposta("516.303.020-58","email@teste.com",
                "Joao Teste","Rua Teste n 123",new BigDecimal("2000.00"));
        Cartao cartao = new Cartao("1111-2222-3333-4444", LocalDateTime.now(), "Dono Cartao", 1000);
        proposta.setCartao(cartao);
        propostaRepository.save(proposta);
        idCartao = proposta.getCartao().getId();
        Uri += "/" + idCartao + "/carteiras";
    }

    @ParameterizedTest
    @CsvSource(value = {"PAYPAL","SAMSUNG_PAY"})
    void deveAssociarUmaCarteiraDigital(NomeCarteira carteira) throws Exception {
        CarteiraDigitalRequest carteiraDigitalRequest = new CarteiraDigitalRequest("email@teste.com", carteira);
        Mockito.when(contaClient.solicitaAssociacaoCarteira(Mockito.any(),Mockito.any())).thenReturn(new SolicitacaoCarteiraDigitalResponse(ResultadoCarteira.ASSOCIADA, UUID.randomUUID().toString()));
        String request = mapper.writeValueAsString(carteiraDigitalRequest);
        MockHttpServletRequestBuilder consultaRequest = post(Uri).contentType(MediaType.APPLICATION_JSON).content(request);
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("**/" + Uri + "/{id}"))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null,null","'',null"},nullValues = {"null"},emptyValue = "")
    void deveRetornarBadRequestCasoTenhaDadosNullosOuEmBranco(String email, NomeCarteira carteira) throws Exception {
        CarteiraDigitalRequest carteiraDigitalRequest = new CarteiraDigitalRequest(email, carteira);
        String request = mapper.writeValueAsString(carteiraDigitalRequest);
        MockHttpServletRequestBuilder consultaRequest = post(Uri).contentType(MediaType.APPLICATION_JSON).content(request);
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(
                        status().isBadRequest()
                );
    }

    @Test
    void deveRetornar404CasoCartaoNaoExista() throws Exception {
        CarteiraDigitalRequest carteiraDigitalRequest = new CarteiraDigitalRequest("email@teste.com", NomeCarteira.PAYPAL);
        String request = mapper.writeValueAsString(carteiraDigitalRequest);
        MockHttpServletRequestBuilder consultaRequest = post(Uri + "404").contentType(MediaType.APPLICATION_JSON).content(request);
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @ParameterizedTest
    @CsvSource(value = {"PAYPAL","SAMSUNG_PAY"})
    @Transactional
    void deveRetornar422CasoCartaoJaEstejaAssociadoAEssaCarteira(NomeCarteira carteira) throws Exception {
        CarteiraDigitalRequest carteiraDigitalRequest = new CarteiraDigitalRequest("email@teste.com", carteira);
        Mockito.when(contaClient.solicitaAssociacaoCarteira(Mockito.any(),Mockito.any())).thenReturn(new SolicitacaoCarteiraDigitalResponse(ResultadoCarteira.ASSOCIADA, UUID.randomUUID().toString()));
        String request = mapper.writeValueAsString(carteiraDigitalRequest);
        MockHttpServletRequestBuilder consultaRequest = post(Uri).contentType(MediaType.APPLICATION_JSON).content(request);
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("**/" + Uri + "/{id}"))
                .andExpect(status().isCreated());
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}