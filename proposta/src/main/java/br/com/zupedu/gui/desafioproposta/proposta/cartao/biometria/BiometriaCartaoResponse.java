package br.com.zupedu.gui.desafioproposta.proposta.cartao.biometria;

import java.time.LocalDateTime;

public class BiometriaCartaoResponse {
    private Long id;
    private String fingerPrint;
    private LocalDateTime dataCadastro = LocalDateTime.now();

    public BiometriaCartaoResponse(Biometria biometria) {
        this.id = biometria.getId();
        this.fingerPrint = biometria.getFingerPrint();
        this.dataCadastro = biometria.getDataCadastro();
    }

    public Long getId() {
        return id;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
