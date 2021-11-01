package br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio;

import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cartoes/bloqueios")
public class BloqueioController {
    @Autowired
    CartaoRepository cartaoRepository;

    private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);

    @PostMapping("/{idCartao}")
    @Transactional
    public void realizaBloqueio(@PathVariable Long idCartao, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request){
        Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(() -> new EntityNotFoundException("Cartao nao encontrado"));
        Bloqueio bloqueio = new Bloqueio(request.getRemoteAddr(),userAgent,cartao);
        cartao.bloquear(bloqueio);
        cartaoRepository.save(cartao);
        logger.info("Bloqueio: bloqueio id={} realizado com sucesso para o cartão id={}",
                cartao.getBloqueio().getId(),cartao.getId());
    }
}
