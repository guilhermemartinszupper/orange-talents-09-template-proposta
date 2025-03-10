package br.com.zupedu.gui.desafioproposta.proposta.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "analise-api",url = "${analise.host}")
public interface SolicitacaoAnaliseClient {
    @PostMapping("/api/solicitacao")
    public SolicitacaoAnaliseResponse solicitacao(SolicitacaoAnaliseRequest request);
}
