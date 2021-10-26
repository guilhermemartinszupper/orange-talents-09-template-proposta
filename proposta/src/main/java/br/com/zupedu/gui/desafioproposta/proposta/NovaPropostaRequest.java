package br.com.zupedu.gui.desafioproposta.proposta;

import br.com.zupedu.gui.desafioproposta.commons.DocumentoExiste;
import br.com.zupedu.gui.desafioproposta.commons.ValidaDocumento;
import br.com.zupedu.gui.desafioproposta.proposta.analise.StatusProposta;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class NovaPropostaRequest {
    @NotBlank @ValidaDocumento @DocumentoExiste(domainClass = Proposta.class,nomeCampo = "documento")
    private String documento;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull @PositiveOrZero
    private BigDecimal salarioBruto;

    public NovaPropostaRequest(String documento, String email, String nome, String endereco, BigDecimal salarioBruto) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salarioBruto = salarioBruto;
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

    public Proposta toModel() {
        Proposta proposta = new Proposta(this.documento,this.email,this.nome,this.endereco,this.salarioBruto);
        proposta.setStatusProposta(StatusProposta.EM_ANALISE);
        return proposta;
    }
}
