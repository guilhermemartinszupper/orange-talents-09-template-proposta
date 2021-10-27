package br.com.zupedu.gui.desafioproposta.proposta;

import br.com.zupedu.gui.desafioproposta.proposta.analise.SolicitacaoAnaliseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

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
        NovaPropostaResponse novaPropostaResponse = new NovaPropostaResponse(proposta);
        URI uri = builder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).body(novaPropostaResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaDetalhadaResponse> obtemProposta(@PathVariable Long id){
        Proposta proposta = propostaRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("Proposta Nao Encontrada");
        });
        return ResponseEntity.ok(new PropostaDetalhadaResponse(proposta));
    }


    private void novaPropostaCriada(Proposta proposta) {
        String documento = proposta.getDocumento().substring(0,3)
                + "***-"
                + proposta.getDocumento().substring(proposta.getDocumento().length() - 2);
        logger.info("Proposta id={}, documento={}, salário={} criada com sucesso!",
                proposta.getId(),documento, proposta.getSalarioBruto());
    }
}
