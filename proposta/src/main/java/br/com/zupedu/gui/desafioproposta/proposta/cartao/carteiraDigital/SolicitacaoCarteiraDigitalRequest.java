package br.com.zupedu.gui.desafioproposta.proposta.cartao.carteiraDigital;

public class SolicitacaoCarteiraDigitalRequest {

    private String email;
    private NomeCarteira carteira;

    public SolicitacaoCarteiraDigitalRequest(String email, NomeCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public NomeCarteira getCarteira() {
        return carteira;
    }
}
