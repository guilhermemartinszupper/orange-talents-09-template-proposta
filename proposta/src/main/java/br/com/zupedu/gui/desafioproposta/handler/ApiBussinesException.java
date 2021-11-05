package br.com.zupedu.gui.desafioproposta.handler;

import org.springframework.http.HttpStatus;

public class ApiBussinesException extends RuntimeException{
    private String campo;
    private String message;
    private HttpStatus HttpStatus;

    public ApiBussinesException(String campo, String message, HttpStatus httpStatus) {
        this.campo = campo;
        this.message = message;
        HttpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus;
    }

    public String getCampo() {
        return campo;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
