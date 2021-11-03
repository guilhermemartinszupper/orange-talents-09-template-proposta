package br.com.zupedu.gui.desafioproposta.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestControllerAdviceHandler {

    @Autowired
    MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<ErroDTO> erros = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            ErroDTO erroDTO = new ErroDTO(fieldError.getField(),message);
            erros.add(erroDTO);
        });
        List<ObjectError> objectErrors = exception.getBindingResult().getGlobalErrors();
        objectErrors.forEach(objectError -> {
            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
            ErroDTO erroDTO = new ErroDTO(objectError.getObjectName(),message);
            erros.add(erroDTO);
        });
        return erros;
    }
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DocumentoRepetidoException.class)
    public ErroDTO handleDocumentoRepetidoException(DocumentoRepetidoException exception){
        return new ErroDTO("documento",exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(FalhaAoBloquearException.class)
    public ErroDTO handleDocumentoRepetidoException(FalhaAoBloquearException exception){
        return new ErroDTO("bloqueio",exception.getMessage());
    }
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(FalhaAoNotificarViagemException.class)
    public ErroDTO handleDocumentoRepetidoException(FalhaAoNotificarViagemException exception){
        return new ErroDTO("aviso viagem",exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErroDTO handleDocumentoRepetidoException(EntityNotFoundException exception){
        return new ErroDTO("id",exception.getMessage());
    }



}
