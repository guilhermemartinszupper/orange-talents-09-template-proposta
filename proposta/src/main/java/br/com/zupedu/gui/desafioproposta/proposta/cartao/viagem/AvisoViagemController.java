package br.com.zupedu.gui.desafioproposta.proposta.cartao.viagem;

import br.com.zupedu.gui.desafioproposta.handler.ApiBussinesException;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.CartaoRepository;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.ContaClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/cartoes/viagens")
public class AvisoViagemController {
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private ContaClient contaClient;

    private final Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);

    @PostMapping("/{idCartao}")
    @Transactional
    public void notificarViagem(@PathVariable Long idCartao, @Valid @RequestBody AvisoViagemRequest avisoViagemRequest, HttpServletRequest httpRequest){
        Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(() -> new EntityNotFoundException("Cartao nao encontrado"));
        AvisoViagem avisoViagem = avisoViagemRequest.toModel(cartao,httpRequest.getLocalAddr(),httpRequest.getHeader("User-Agent"));
        cartao.adicionaAvisoViagem(avisoViagem);
        try {
            NotificacaoAvisoViagemResponse viagemResponse = contaClient
                    .notificarViagem(new NotificacaoAvisoViagemRequest(avisoViagem),cartao.getNumeroCartao());
            if(viagemResponse.getResultado().equals(ViagemResultado.CRIADO)){
                cartaoRepository.save(cartao);
                logger.info("Aviso Viagem id={} associada ao cartao id={}",cartao.getUltimaViagem().getId(),cartao.getId());
            }
        }catch (FeignException.UnprocessableEntity e){
            logger.error("Nao Foi Possivel Associar Viagem ao Cartao error= {}",e.getMessage());
            throw new ApiBussinesException("Aviso Viagem","Falha ao Notificar Viagem", HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (FeignException e){
            logger.error("O serviço esta fora do ar");
            logger.error(e.contentUTF8());
            throw new ApiBussinesException("Aviso Viagem","Falha ao Notificar Viagem", HttpStatus.BAD_GATEWAY);
        }
    }
}
