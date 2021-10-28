package br.com.zupedu.gui.desafioproposta.proposta.cartao.biometria;

import br.com.zupedu.gui.desafioproposta.commons.ValidaBiometria;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;

import javax.validation.constraints.NotBlank;

public class NovaBiometriaRequest {
    @NotBlank @ValidaBiometria
    private String fingerPrint;

    @Deprecated
    public NovaBiometriaRequest() {
    }

    public NovaBiometriaRequest(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(this.fingerPrint,cartao);
    }
}
