package br.com.zupedu.gui.desafioproposta.proposta.cartao.bloqueio;

public class SolicitacaoBloqueioRequest {
    private String sistemaResponsavel;

    public SolicitacaoBloqueioRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
