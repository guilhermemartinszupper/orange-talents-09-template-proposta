package br.com.zupedu.gui.desafioproposta.proposta.cartao.carteiraDigital;


import br.com.zupedu.gui.desafioproposta.handler.ApiBussinesException;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.CartaoRepository;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.ContaClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cartoes")
public class CarteiraDigitalController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private ContaClient contaClient;

    private final Logger logger = LoggerFactory.getLogger(CarteiraDigitalController.class);

    @PostMapping("/{idCartao}/carteiras")
    @Transactional
    public ResponseEntity<?> associaCarteiraDigital(@PathVariable Long idCartao,
                                                    @Valid @RequestBody CarteiraDigitalRequest carteiraDigitalRequest,
                                                    UriComponentsBuilder builder){
        Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(() -> new EntityNotFoundException("Cartao nao encontrado"));
        if(cartao.jaPossuiEssaCarteira(carteiraDigitalRequest.getCarteira())){
            throw new ApiBussinesException("Carteira Digital","Esse Cartao Ja Associado a Essa Carteira Digital", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            logger.info("Realizando Request ao endpoint de CarteiraDigital");
            SolicitacaoCarteiraDigitalResponse solicitacaoCarteiraDigitalResponse =
                    contaClient.solicitaAssociacaoCarteira(new SolicitacaoCarteiraDigitalRequest(carteiraDigitalRequest.getEmail()
                                                                                                ,carteiraDigitalRequest.getCarteira())
                                                                                                ,cartao.getNumeroCartao());
            if(!solicitacaoCarteiraDigitalResponse.getResultado().equals(ResultadoCarteira.ASSOCIADA)){
                logger.error("Erro durante Associação! Resultado: {}", solicitacaoCarteiraDigitalResponse.getResultado());
                throw new ApiBussinesException("Carteira Digital","Falha ao associar cartao", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            CarteiraDigital carteiraDigital = new CarteiraDigital(carteiraDigitalRequest.getCarteira(),
                                                                    carteiraDigitalRequest.getEmail(),
                                                                    solicitacaoCarteiraDigitalResponse.getId(),
                                                                    cartao);
            cartao.associaCarteira(carteiraDigital);
            cartaoRepository.save(cartao);
            logger.info("Carteira id={} associada com sucesso ao cartao id={}",cartao.getUltimaCarteira().getId(),cartao.getId());
            URI uri = builder.path("/cartoes/{idCartao}/carteiras/{idCarteira}").buildAndExpand(cartao.getId(),cartao.getUltimaCarteira().getId()).toUri();
            return ResponseEntity.created(uri).build();
        }catch (FeignException.UnprocessableEntity e){
            logger.error("Erro durante Associação de Carteira Digital: {}", e.getMessage());
            throw new ApiBussinesException("Carteira Digital","Falha ao associar cartao", HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (FeignException e){
            logger.error("Erro durante Associação de Carteira Digital: {}", e.getMessage());
            throw new ApiBussinesException("Carteira Digital","Serviço Indisponivel",HttpStatus.BAD_GATEWAY);
        }
    }
}
