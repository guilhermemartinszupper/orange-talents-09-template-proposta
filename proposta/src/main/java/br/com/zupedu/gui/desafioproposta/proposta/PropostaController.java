package br.com.zupedu.gui.desafioproposta.proposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    PropostaRepository propostaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<NovaPropostaResponse>  cadastraProposta(@Valid @RequestBody NovaPropostaRequest propostaRequest,
                                                                  UriComponentsBuilder builder){
        Proposta proposta = propostaRequest.toModel();
        NovaPropostaResponse novaPropostaResponse = new NovaPropostaResponse(propostaRepository.save(proposta));
        URI uri = builder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).body(novaPropostaResponse);
    }
}
