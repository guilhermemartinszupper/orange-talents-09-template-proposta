package br.com.zupedu.gui.desafioproposta.proposta.cartao;

import br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio.SolicitacaoBloqueioRequest;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio.SolicitacaoBloqueioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "conta-api",url = "${conta.host}")
public interface ContaClient {
    @GetMapping("/api/cartoes")
    public CartaoResponse buscaCartao(@RequestParam String idProposta);
    @PostMapping("/api/cartoes/{id}/bloqueios")
    public SolicitacaoBloqueioResponse solicitarBloqueio(@PathVariable String id, SolicitacaoBloqueioRequest bloqueioRequest);
}