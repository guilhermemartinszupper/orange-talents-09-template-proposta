package br.com.zupedu.gui.desafioproposta.proposta.cartao;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.biometria.BiometriaCartaoResponse;

public class CartaoPropostaResponse {
    private Long id;
    private String numeroCartao;
    private LocalDateTime emitidoEm;
    private String titular;
    private Integer limite;
    private List<BiometriaCartaoResponse> biometrias = new ArrayList<>();

    public CartaoPropostaResponse(Cartao cartao) {
        this.id = cartao.getId();
        this.numeroCartao = cartao.getNumeroCartao();
        this.emitidoEm = cartao.getEmitidoEm();
        this.titular = cartao.getTitular();
        this.limite = cartao.getLimite();
        this.biometrias = cartao.getBiometrias().stream().map(BiometriaCartaoResponse::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getNumeroCartao() {
        return numeroCartao;
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

    public List<BiometriaCartaoResponse> getBiometrias() {
        return biometrias;
    }
}
