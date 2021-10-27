package br.com.zupedu.gui.desafioproposta.proposta.cartao;

import br.com.zupedu.gui.desafioproposta.proposta.Proposta;
import br.com.zupedu.gui.desafioproposta.proposta.PropostaRepository;
import br.com.zupedu.gui.desafioproposta.proposta.analise.StatusProposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssociaPropostaAoCartaoScheduler {

    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private ContaClient contaClient;
    private final Logger logger = LoggerFactory.getLogger(AssociaPropostaAoCartaoScheduler
            .class);

    @Scheduled(fixedDelay = 10 * 1000) //10 segundos
    protected void associaCartao(){
        List<Proposta> propostas = propostaRepository.findAllByStatusPropostaAndCartao(StatusProposta.ELEGIVEL, null, PageRequest.ofSize(100));
        if(!propostas.isEmpty()){
            logger.info("Iniciado a associação de cartões");
            propostas.forEach(proposta -> {
                try {
                    CartaoResponse cartaoResponse = contaClient.buscaCartao(proposta.getId().toString());
                    Cartao cartao = cartaoResponse.toModel();
                    proposta.setCartao(cartao);
                    propostaRepository.save(proposta);
                    logger.info("Cartao da Proposta id={} final associado: {}",
                            proposta.getId(),cartao.ofuscaIdCartao());
                }catch (Exception e){
                    logger.info("Cartao da Proposta id={} ainda nao foi gerado",
                            proposta.getId());
                    logger.warn("Erro ao associar: " + e.getMessage());
                }
            });
            logger.info("Finalizado a associação de cartões");
        }
    }
}
