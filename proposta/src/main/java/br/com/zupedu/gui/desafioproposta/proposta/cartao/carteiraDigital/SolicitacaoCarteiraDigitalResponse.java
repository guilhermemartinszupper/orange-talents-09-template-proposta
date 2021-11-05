package br.com.zupedu.gui.desafioproposta.proposta.cartao.carteiraDigital;


public class SolicitacaoCarteiraDigitalResponse {
    private ResultadoCarteira resultado;
    private String id;

    public SolicitacaoCarteiraDigitalResponse() {
    }

    public SolicitacaoCarteiraDigitalResponse(ResultadoCarteira resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public ResultadoCarteira getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
