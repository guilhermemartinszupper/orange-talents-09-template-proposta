package br.com.zupedu.gui.desafioproposta.proposta.cartao;

import br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio.SolicitacaoBloqueioRequest;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio.SolicitacaoBloqueioResponse;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.viagem.NotificacaoAvisoViagemRequest;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.viagem.NotificacaoAvisoViagemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "conta-api",url = "${conta.host}")
public interface ContaClient {
    @GetMapping("/api/cartoes")
    public CartaoResponse buscaCartao(@RequestParam String idProposta);
    @PostMapping("/api/cartoes/{id}/bloqueios")
    public SolicitacaoBloqueioResponse solicitarBloqueio(@PathVariable String id, SolicitacaoBloqueioRequest bloqueioRequest);
    @PostMapping("/api/cartoes/{id}/avisos")
    public NotificacaoAvisoViagemResponse notificarViagem(@RequestBody NotificacaoAvisoViagemRequest viagemRequest, @PathVariable String id);
}