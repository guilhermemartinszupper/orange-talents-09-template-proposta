package br.com.zupedu.gui.desafioproposta.proposta.cartao.viagem;

import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AvisoViagemRequest {
    @NotBlank
    private String destino;
    @FutureOrPresent @NotNull
    private LocalDate dataTermino;
    private LocalDateTime instanteAviso = LocalDateTime.now();

    public AvisoViagemRequest(String destino, LocalDate dataTermino) {
        this.destino = destino;
        this.dataTermino = dataTermino;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public LocalDateTime getInstanteAviso() {
        return instanteAviso;
    }


    public AvisoViagem toModel(Cartao cartao, String ipCliente,String userAgent) {
        return new AvisoViagem(this.destino,this.dataTermino,this.instanteAviso,ipCliente,userAgent,cartao);
    }
}
