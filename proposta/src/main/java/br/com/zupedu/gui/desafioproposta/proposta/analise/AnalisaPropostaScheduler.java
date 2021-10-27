package br.com.zupedu.gui.desafioproposta.proposta.analise;

import br.com.zupedu.gui.desafioproposta.proposta.Proposta;
import br.com.zupedu.gui.desafioproposta.proposta.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnalisaPropostaScheduler {
    @Autowired
    SolicitacaoAnaliseClient analiseClient;

    @Autowired
    PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(AnalisaPropostaScheduler.class);


    private void analisaProposta(Proposta proposta) {
        SolicitacaoAnaliseRequest request = new SolicitacaoAnaliseRequest(proposta);
        try {
            analiseClient.solicitacao(request);
            proposta.setStatusProposta(StatusProposta.ELEGIVEL);
        }catch (FeignException.UnprocessableEntity e){
            proposta.setStatusProposta(StatusProposta.NAO_ELEGIVEL);
        }catch (Exception e){
            logger.warn("Houve algum erro na analise da proposta:" + e.getMessage());
            proposta.setStatusProposta(StatusProposta.EM_ANALISE);
        }
        logger.info("Status da Proposta id={} definido: {}",
                proposta.getId(),proposta.getStatusProposta() );
    }

    @Scheduled(fixedDelay = 10000) //10 segundos
    protected void analisaNovamentePropostasEmAnalise(){
        List<Proposta> propostas = propostaRepository.findAllByStatusProposta(StatusProposta.EM_ANALISE, PageRequest.ofSize(2));
        if(!propostas.isEmpty()){
            logger.info("executando analises em propostas ainda nao analisadas");
            propostas.forEach(this::analisaProposta);
            propostaRepository.saveAll(propostas);
        }
    }

}
