package br.com.zupedu.gui.desafioproposta.proposta.cartao.biometria;

import br.com.zupedu.gui.desafioproposta.proposta.cartao.AssociaPropostaAoCartaoScheduler;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.CartaoRepository;
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
@RequestMapping("/cartoes/biometrias")
public class BiometriaController {

    @Autowired
    CartaoRepository cartaoRepository;

    private final Logger logger = LoggerFactory.getLogger(BiometriaController.class);

    @PostMapping("/{idCartao}")
    @Transactional
    public ResponseEntity<?> cadastraBiometria(@PathVariable Long idCartao, @Valid @RequestBody NovaBiometriaRequest biometriaRequest, UriComponentsBuilder builder){
        Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(() -> new EntityNotFoundException("Cartao nao encontrado"));
        Biometria biometria = biometriaRequest.toModel(cartao);
        cartao.adicionaBiometria(biometria);
        cartaoRepository.save(cartao);
        biometria = cartao.getBiometria(biometria);
        logger.info("Biometria: biometria id={} cadastrada com sucesso para o cartão id={}",
                biometria.getId(),cartao.getId());
        URI uri = builder.path("/biometrias/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
