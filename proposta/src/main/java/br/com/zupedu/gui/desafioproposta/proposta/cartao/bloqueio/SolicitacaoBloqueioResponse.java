package br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio;

public class SolicitacaoBloqueioResponse {
    private ResultadoBloqueio resultado;

    public SolicitacaoBloqueioResponse() {
    }

    public SolicitacaoBloqueioResponse(ResultadoBloqueio resultado) {
        this.resultado = resultado;
    }

    public ResultadoBloqueio getResultado() {
        return resultado;
    }
}
