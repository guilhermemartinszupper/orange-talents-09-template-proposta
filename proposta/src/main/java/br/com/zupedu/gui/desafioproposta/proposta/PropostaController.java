package br.com.zupedu.gui.desafioproposta.proposta;

import br.com.zupedu.gui.desafioproposta.proposta.analise.SolicitacaoAnaliseClient;
import br.com.zupedu.gui.desafioproposta.proposta.analise.SolicitacaoAnaliseRequest;
import br.com.zupedu.gui.desafioproposta.proposta.analise.StatusProposta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    SolicitacaoAnaliseClient analiseClient;

    @Autowired
    PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    @PostMapping
    @Transactional
    public ResponseEntity<NovaPropostaResponse>  cadastraProposta(@Valid @RequestBody NovaPropostaRequest propostaRequest,
                                                                  UriComponentsBuilder builder){
        Proposta proposta = propostaRequest.toModel();
        propostaRepository.save(proposta);
        novaPropostaCriada(proposta);
        analisaProposta(proposta);
        NovaPropostaResponse novaPropostaResponse = new NovaPropostaResponse(proposta);
        URI uri = builder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).body(novaPropostaResponse);
    }
    //Faz a analise da proposta, caso ocorra uma exceção o status fica em_analise e é revisto mais tarde.
    @Transactional
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

    //Analisa novamente as propostas que ainda estao com em_analise, pois isso significa que houve uma excessao
//    @Scheduled(fixedDelay = 1800000) //30 minutos
    @Scheduled(fixedDelay = 10000) //10 segundos
    protected void analisaNovamentePropostasEmAnalise(){
        List<Proposta> propostas = propostaRepository.findAllByStatusProposta(StatusProposta.EM_ANALISE,PageRequest.ofSize(2));
        if(!propostas.isEmpty()){
            logger.info("executando novamente analises em propostas ainda nao analisadas");
            propostas.forEach(this::analisaProposta);
            propostaRepository.saveAll(propostas);
        }
    }

    //Exibe um Log da nova proposta criada mascarando os dados sensiveis
    private void novaPropostaCriada(Proposta proposta) {
        String documento = proposta.getDocumento().substring(0,3)
                + "***-"
                + proposta.getDocumento().substring(proposta.getDocumento().length() - 2);
        logger.info("Proposta id={}, documento={}, salário={} criada com sucesso!",
                proposta.getId(),documento, proposta.getSalarioBruto());
    }
}
