package br.com.zupedu.gui.desafioproposta.proposta.analise;

import org.springframework.util.Assert;

public class SolicitacaoAnaliseResponse {
    private String documento;
    private String nome;
    private ResultadoAnalise resultadoSolicitacao;
    private String idProposta;

    public SolicitacaoAnaliseResponse(String documento, String nome, ResultadoAnalise resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public ResultadoAnalise getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }

    @Override
    public String toString() {
        return "SolicitacaoAnaliseResponse{" +
                "documento='" + documento + '\'' +
                ", nome='" + nome + '\'' +
                ", resultadoSolicitacao=" + resultadoSolicitacao +
                ", idProposta='" + idProposta + '\'' +
                '}';
    }
}
