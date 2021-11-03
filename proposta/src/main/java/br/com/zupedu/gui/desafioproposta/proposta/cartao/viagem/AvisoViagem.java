package br.com.zupedu.gui.desafioproposta.proposta.cartao.viagem;

import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String destino;
    private LocalDate dataTermino;
    private LocalDateTime instanteAviso = LocalDateTime.now();
    private String ipCliente;
    private String userAgent;
    @ManyToOne(optional = false)
    private Cartao cartao;

    @Deprecated
    public AvisoViagem() {
    }

    public AvisoViagem(String destino, LocalDate dataTermino, LocalDateTime instanteAviso, String ipCliente, String userAgent,Cartao cartao) {
        Assert.notNull(cartao, "Cartao nao pode ser nulo");
        Assert.isTrue(destino != null && !destino.isEmpty(), "destino nao pode ser nulo nem vazio");
        Assert.isTrue(dataTermino != null && !dataTermino.isBefore(LocalDate.now()), "data de termino nao pode ser nulo e nem no passado");
        Assert.isTrue(ipCliente != null && !ipCliente.isEmpty(), "ipCliente nao pode ser nulo nem vazio");
        Assert.isTrue(userAgent != null && !userAgent.isEmpty(), "userAgent nao pode ser nulo nem vazio");
        this.destino = destino;
        this.dataTermino = dataTermino;
        this.instanteAviso = instanteAviso;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }


    public Long getId() {
        return id;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }
}
