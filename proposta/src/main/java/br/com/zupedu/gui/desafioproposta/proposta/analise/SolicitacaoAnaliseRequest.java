package br.com.zupedu.gui.desafioproposta.proposta.analise;

import br.com.zupedu.gui.desafioproposta.proposta.Proposta;
import org.springframework.util.Assert;

public class SolicitacaoAnaliseRequest {
    private String documento;
    private String nome;
    private String idProposta;

    public SolicitacaoAnaliseRequest(Proposta proposta) {
        Assert.notNull(proposta,"proposta nao pode ser nula");
        Assert.isTrue(proposta.getDocumentoCriptografado() != null && !proposta.getDocumentoCriptografado().isEmpty(),"documento nao pode ser nulo nem vazio");
        Assert.isTrue(proposta.getNome() != null && !proposta.getNome().isEmpty(),"nome nao pode ser nulo nem vazio");
        Assert.isTrue(proposta.getId() != null,"idProposta nao pode ser nulo");
        this.documento = proposta.getDocumentoCriptografado();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}

