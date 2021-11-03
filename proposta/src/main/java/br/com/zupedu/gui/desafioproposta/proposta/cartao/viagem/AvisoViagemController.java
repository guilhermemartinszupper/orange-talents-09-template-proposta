package br.com.zupedu.gui.desafioproposta.proposta.cartao.viagem;

import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);

    @PostMapping("/{idCartao}")
    @Transactional
    public void notificarViagem(@PathVariable Long idCartao, @Valid @RequestBody AvisoViagemRequest avisoViagemRequest, HttpServletRequest httpRequest){
        Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(() -> new EntityNotFoundException("Cartao nao encontrado"));
        AvisoViagem avisoViagem = avisoViagemRequest.toModel(cartao,httpRequest.getLocalAddr(),httpRequest.getHeader("User-Agent"));
        cartao.adicionaAvisoViagem(avisoViagem);
        cartaoRepository.save(cartao);
        logger.info("Aviso Viagem id={} associada ao cartao id={}",cartao.getUltimaViagem().getId(),cartao.getId());
    }
}
