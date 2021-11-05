package br.com.zupedu.gui.desafioproposta.handler;

import java.time.LocalDateTime;

public class ErroDTO {
    private String campo;
    private String mensagem;
    private LocalDateTime instante;

    public ErroDTO(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
        this.instante = LocalDateTime.now();
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getInstante() {
        return instante;
    }
}
