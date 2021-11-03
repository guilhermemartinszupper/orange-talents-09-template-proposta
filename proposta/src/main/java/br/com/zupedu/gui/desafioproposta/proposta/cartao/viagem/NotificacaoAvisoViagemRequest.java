package br.com.zupedu.gui.desafioproposta.proposta.cartao.viagem;

import java.time.LocalDate;

public class NotificacaoAvisoViagemRequest {
    private String destino;
    private LocalDate validoAte;

    public NotificacaoAvisoViagemRequest(AvisoViagem avisoViagem) {
        this.destino = avisoViagem.getDestino();
        this.validoAte = avisoViagem.getDataTermino();
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
