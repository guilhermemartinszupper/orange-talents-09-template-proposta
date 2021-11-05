package br.com.zupedu.gui.desafioproposta.proposta.cartao.viagem;

import br.com.zupedu.gui.desafioproposta.proposta.Proposta;
import br.com.zupedu.gui.desafioproposta.proposta.PropostaRepository;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.ContaClient;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio.ResultadoBloqueio;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio.SolicitacaoBloqueioResponse;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
@ActiveProfiles(profiles = "test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class AvisoViagemControllerTest {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PropostaRepository propostaRepository;
    @MockBean
    ContaClient contaClient;

    String URI = "/cartoes/viagens";

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
    void deveNotificarUmAvisoDeViagem() throws Exception {
        AvisoViagemRequest avisoViagemRequest = new AvisoViagemRequest("Salvador", LocalDate.now().plusDays(5L));
        Mockito.when(contaClient.notificarViagem(Mockito.any(),Mockito.any())).thenReturn(new NotificacaoAvisoViagemResponse(ViagemResultado.CRIADO));
        String request = mapper.writeValueAsString(avisoViagemRequest);
        MockHttpServletRequestBuilder consultaRequest = post(URI).contentType(MediaType.APPLICATION_JSON).content(request)
                .header("User-Agent","PostmanRuntime/7.28.4");
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null,null","'',null"},nullValues = {"null"},emptyValue = "")
    void deveRetornarBadRequestCasoTenhaDadosNullosOuEmBranco(String destino,LocalDate termino) throws Exception {
        AvisoViagemRequest avisoViagemRequest = new AvisoViagemRequest(destino, termino);
        String request = mapper.writeValueAsString(avisoViagemRequest);
        MockHttpServletRequestBuilder consultaRequest = post(URI).contentType(MediaType.APPLICATION_JSON).content(request)
                .header("User-Agent","PostmanRuntime/7.28.4");
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(
                        status().isBadRequest()
                );
    }

    @Test
    void deveRetornar404CasoCartaoNaoExista() throws Exception {
        AvisoViagemRequest avisoViagemRequest = new AvisoViagemRequest("Salvador", LocalDate.now().plusDays(5L));
        String request = mapper.writeValueAsString(avisoViagemRequest);
        MockHttpServletRequestBuilder consultaRequest = post(URI + "404").contentType(MediaType.APPLICATION_JSON).content(request)
                .header("User-Agent","PostmanRuntime/7.28.4");
        mockMvc.perform(consultaRequest)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}