package br.com.zupedu.gui.desafioproposta.proposta.cartao;

import br.com.zupedu.gui.desafioproposta.handler.CartaoJaEstaBloqueadoException;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.biometria.Biometria;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio.Bloqueio;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Cartao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCartao;
    private LocalDateTime emitidoEm;
    private String titular;
    private Integer limite;
    @OneToMany(mappedBy = "cartao",cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    private List<Biometria> biometrias = new ArrayList<>();
    @OneToOne(mappedBy = "cartao",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Bloqueio bloqueio;

    @Deprecated
    public Cartao() {
    }

    public Cartao(String numeroCartao, LocalDateTime emitidoEm, String titular, Integer limite) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
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

    public List<Biometria> getBiometrias() {
        return biometrias;
    }

    public Biometria getBiometria(Biometria biometria){
        int p = biometrias.lastIndexOf(biometria);
        return biometrias.get(p);
    }

    public Bloqueio getBloqueio() {
        return bloqueio;
    }

    public String ofuscaNumeroCartao() {
        return numeroCartao.substring(numeroCartao.length() - 4);
    }

    public void adicionaBiometria(Biometria biometria) {
        Assert.notNull(biometria, "biometria nao pode ser null");
        biometrias.add(biometria);
    }

    public void bloquear(Bloqueio bloqueio) {
        if(this.bloqueio != null){
            throw new CartaoJaEstaBloqueadoException("Esse Cartão Ja Possui Um Bloqueio");
        }
        this.bloqueio = bloqueio;

    }
}
