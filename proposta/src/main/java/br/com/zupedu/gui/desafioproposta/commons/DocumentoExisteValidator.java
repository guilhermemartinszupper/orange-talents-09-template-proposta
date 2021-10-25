package br.com.zupedu.gui.desafioproposta.commons;
import br.com.zupedu.gui.desafioproposta.handler.DocumentoRepetidoException;
import br.com.zupedu.gui.desafioproposta.proposta.Proposta;
import br.com.zupedu.gui.desafioproposta.proposta.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Optional;

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
            throw new DocumentoRepetidoException("Documento Ja esta atrelado a uma proposta");
        }
        return true;
    }
}
