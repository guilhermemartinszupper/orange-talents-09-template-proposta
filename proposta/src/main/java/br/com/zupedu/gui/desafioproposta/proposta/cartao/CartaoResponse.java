package br.com.zupedu.gui.desafioproposta.proposta.cartao;

import java.time.LocalDateTime;

public class CartaoResponse {
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private Integer limite;

    public CartaoResponse(String id, LocalDateTime emitidoEm, String titular, Integer limite) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public Integer getLimite() {
        return limite;
    }


    public Cartao toModel() {
        return new Cartao(this.id,this.emitidoEm,this.titular,this.limite);
    }
}
