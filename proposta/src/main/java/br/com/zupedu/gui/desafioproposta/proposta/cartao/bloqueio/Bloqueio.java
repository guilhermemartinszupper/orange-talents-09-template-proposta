package br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio;

import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ipCliente;
    private String userAgent;
    private LocalDateTime instanteBloqueio = LocalDateTime.now();
    @ManyToOne(cascade = CascadeType.ALL)
    private Cartao cartao;
    @Enumerated(EnumType.STRING)
    private ResultadoBloqueio resultadoBloqueio;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(String ipCliente, String userAgent, Cartao cartao) {
        Assert.isTrue(ipCliente != null && !ipCliente.isEmpty(),"ipCliente nao pode ser null nem vazio");
        Assert.isTrue(userAgent != null && !userAgent.isEmpty(),"userAgent nao pode ser null nem vazio");
        Assert.notNull(cartao,"cartao nao pode ser null");
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public ResultadoBloqueio getResultadoBloqueio() {
        return resultadoBloqueio;
    }

    public void setResultadoBloqueio(ResultadoBloqueio resultadoBloqueio) {
        this.resultadoBloqueio = resultadoBloqueio;
    }

    public Long getId() {
        return id;
    }
}
