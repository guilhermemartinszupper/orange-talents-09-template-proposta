package br.com.zupedu.gui.desafioproposta.proposta;

import br.com.zupedu.gui.desafioproposta.proposta.analise.StatusProposta;

import java.math.BigDecimal;

public class NovaPropostaResponse {
    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salarioBruto;
    private StatusProposta statusProposta;

    public NovaPropostaResponse() {
    }

    public NovaPropostaResponse(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.salarioBruto = proposta.getSalarioBruto();
        this.statusProposta = proposta.getStatusProposta();
    }


    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalarioBruto() {
        return salarioBruto;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }
}
