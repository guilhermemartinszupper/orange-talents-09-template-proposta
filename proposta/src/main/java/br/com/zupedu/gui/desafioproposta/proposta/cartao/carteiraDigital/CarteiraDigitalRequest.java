package br.com.zupedu.gui.desafioproposta.proposta.cartao.carteiraDigital;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraDigitalRequest {
    @NotBlank
    private String email;
    @NotNull
    private NomeCarteira carteira;

    public CarteiraDigitalRequest(String email, NomeCarteira carteira) {
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
