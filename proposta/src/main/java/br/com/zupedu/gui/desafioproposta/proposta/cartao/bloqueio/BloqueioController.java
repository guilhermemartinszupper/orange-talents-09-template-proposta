package br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio;

import br.com.zupedu.gui.desafioproposta.handler.FalhaAoBloquearException;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.CartaoRepository;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.ContaClient;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.StatusCartao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cartoes/bloqueios")
public class BloqueioController {
    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    BloqueioRepository bloqueioRepository;

    @Autowired
    ContaClient contaClient;

    private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);

    @PostMapping("/{idCartao}")
    public void solicitaBloqueio(@PathVariable Long idCartao, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request){
        Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(() -> new EntityNotFoundException("Cartao nao encontrado"));
        if(cartao.getStatusCartao().equals(StatusCartao.BLOQUEADO)){
            logger.info("Cartao id={} ja esta bloqueado!",cartao.getId());
            throw new FalhaAoBloquearException("Cartao Ja Esta Bloqueado");
        }
        logger.info("Cartao ATIVO, Notificando Sistema de Bloqueio");
        notificaBloqueioAoSistemaLegado(cartao,userAgent,request.getLocalAddr());
    }

    public void notificaBloqueioAoSistemaLegado(Cartao cartao, String userAgent, String ipClient){
        Bloqueio bloqueio = new Bloqueio(ipClient,userAgent,cartao);
        try {
            SolicitacaoBloqueioResponse response = contaClient.solicitarBloqueio(cartao.getNumeroCartao(),
                    new SolicitacaoBloqueioRequest("Propostas"));
            if(response.getResultado().equals(ResultadoBloqueio.BLOQUEADO)){
                cartao.bloquear();
                bloqueio.setResultadoBloqueio(ResultadoBloqueio.BLOQUEADO);
                bloqueioRepository.save(bloqueio);
                logger.info("Bloqueio: bloqueio id={} realizado com sucesso para o cartão id={}",
                        bloqueio.getId(),cartao.getId());
            }
        }catch (FeignException.UnprocessableEntity e){
            logger.error("Nao Foi Possivel Bloquear o Cartao error= {}",e.getMessage());
            bloqueio.setResultadoBloqueio(ResultadoBloqueio.FALHA);
            bloqueioRepository.save(bloqueio);
            throw new FalhaAoBloquearException("Nao foi possivel bloquear o cartao, tente mais tarde");
        }catch (FeignException e){
            logger.error("O serviço esta fora do ar");
            bloqueio.setResultadoBloqueio(ResultadoBloqueio.FALHA);
            bloqueioRepository.save(bloqueio);
            throw new FalhaAoBloquearException("Nao foi possivel bloquear o cartao, tente mais tarde");
        }
    }
}
