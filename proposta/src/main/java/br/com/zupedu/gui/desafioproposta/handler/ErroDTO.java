package br.com.zupedu.gui.desafioproposta.handler;

public class ErroDTO {
    private String campo;
    private String descricao;

    public ErroDTO(String campo, String descricao) {
        this.campo = campo;
        this.descricao = descricao;
    }

    public String getCampo() {
        return campo;
    }

    public String getDescricao() {
        return descricao;
    }
}
