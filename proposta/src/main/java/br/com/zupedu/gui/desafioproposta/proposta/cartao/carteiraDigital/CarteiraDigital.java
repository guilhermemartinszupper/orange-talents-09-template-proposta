package br.com.zupedu.gui.desafioproposta.proposta.cartao.carteiraDigital;

import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class CarteiraDigital {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NomeCarteira nomeCarteira;
    private String numeroCarteira;
    private String email;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public CarteiraDigital() {
    }

    public CarteiraDigital(NomeCarteira nomeCarteira, String email, String numeroCarteira, Cartao cartao) {
        Assert.notNull(nomeCarteira,"nomeCarteira nao pode ser null");
        Assert.isTrue(email !=null && !email.isEmpty(),"email nao pode ser null e nem vazio");
        Assert.isTrue(email !=null && !email.isEmpty(),"numeroCarteira nao pode ser null e nem vazio");
        Assert.notNull(cartao,"cartao nao pode ser null");
        this.nomeCarteira = nomeCarteira;
        this.numeroCarteira = numeroCarteira;
        this.email = email;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public NomeCarteira getNomeCarteira() {
        return nomeCarteira;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarteiraDigital that = (CarteiraDigital) o;
        return nomeCarteira == that.nomeCarteira && Objects.equals(cartao, that.cartao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeCarteira, cartao);
    }
}
