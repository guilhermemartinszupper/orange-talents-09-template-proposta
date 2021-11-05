package br.com.zupedu.gui.desafioproposta.commons.validations;
import br.com.zupedu.gui.desafioproposta.handler.ApiBussinesException;
import br.com.zupedu.gui.desafioproposta.proposta.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DocumentoExisteValidator implements ConstraintValidator<DocumentoExiste, String> {

    @Autowired
    PropostaRepository repository;

    @Override
    public void initialize(DocumentoExiste constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s != null && repository.findByDocumento(s.replaceAll("[^a-zA-Z0-9]", "")).isPresent()) {
            throw new ApiBussinesException("Document","Documento Ja esta atrelado a uma proposta", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return true;
    }
}
