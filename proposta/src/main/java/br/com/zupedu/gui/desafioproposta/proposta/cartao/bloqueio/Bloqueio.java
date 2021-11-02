package br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio;

import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;

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
