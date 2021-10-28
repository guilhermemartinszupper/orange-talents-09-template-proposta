package br.com.zupedu.gui.desafioproposta.proposta;

import br.com.zupedu.gui.desafioproposta.proposta.analise.StatusProposta;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.CartaoPropostaResponse;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;

public class PropostaDetalhadaResponse {
    private Long id;
    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salarioBruto;
    private StatusProposta statusProposta;
    private CartaoPropostaResponse cartao;

    public PropostaDetalhadaResponse(Proposta proposta) {
        Assert.notNull(proposta, "proposta nao pode ser null");
        this.id = proposta.getId();
        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.salarioBruto = proposta.getSalarioBruto();
        this.statusProposta = proposta.getStatusProposta();
        if(proposta.getCartao() != null){
            this.cartao = new CartaoPropostaResponse(proposta.getCartao());
        }
    }

    public Long getId() {
        return id;
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

    public CartaoPropostaResponse getCartao() {
        return cartao;
    }
}
