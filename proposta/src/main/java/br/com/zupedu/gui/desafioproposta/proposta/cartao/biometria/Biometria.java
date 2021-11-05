package br.com.zupedu.gui.desafioproposta.proposta.cartao.biometria;

import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Biometria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "text",nullable = false)
    private String fingerPrint;
    @Column(nullable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Cartao cartao;

    @Deprecated
    public Biometria() {
    }

    public Biometria(String fingerPrint, Cartao cartao) {
        Assert.isTrue(fingerPrint != null && !fingerPrint.isEmpty(),"fingerprint nao pode ser null nem vazia");
        Assert.notNull(cartao,"cartao nao pode ser null");
        this.fingerPrint = fingerPrint;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public Cartao getCartao() {
        return cartao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biometria biometria = (Biometria) o;
        return Objects.equals(fingerPrint, biometria.fingerPrint) && Objects.equals(cartao, biometria.cartao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fingerPrint, cartao);
    }
}
