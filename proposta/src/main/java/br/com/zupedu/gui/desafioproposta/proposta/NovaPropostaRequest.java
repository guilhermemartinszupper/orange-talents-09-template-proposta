package br.com.zupedu.gui.desafioproposta.proposta;

import br.com.zupedu.gui.desafioproposta.validator.ValidaDocumento;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class NovaPropostaRequest {
    @NotBlank
    @ValidaDocumento
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

    public Proposta toModel() {
        return new Proposta(this.documento,this.email,this.nome,this.endereco,this.salarioBruto);
    }
}
