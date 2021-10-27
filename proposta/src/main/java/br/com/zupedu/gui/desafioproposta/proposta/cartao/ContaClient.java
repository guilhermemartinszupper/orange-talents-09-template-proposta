package br.com.zupedu.gui.desafioproposta.proposta.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "conta-api",url = "${conta.host}")
public interface ContaClient {
    @GetMapping("/api/cartoes")
    public CartaoResponse buscaCartao(@RequestParam String idProposta);
}