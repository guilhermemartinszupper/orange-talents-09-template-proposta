package br.com.zupedu.gui.desafioproposta.proposta.analise;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "analise-api")
public class analiseSolicitacaoClient {

}
