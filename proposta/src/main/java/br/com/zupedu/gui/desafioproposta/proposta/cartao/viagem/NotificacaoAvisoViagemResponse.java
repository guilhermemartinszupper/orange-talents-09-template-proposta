package br.com.zupedu.gui.desafioproposta.proposta.cartao.viagem;

public class NotificacaoAvisoViagemResponse {
    private ViagemResultado resultado;

    public NotificacaoAvisoViagemResponse() {
    }

    public NotificacaoAvisoViagemResponse(ViagemResultado resultado) {
        this.resultado = resultado;
    }

    public ViagemResultado getResultado() {
        return resultado;
    }
}
