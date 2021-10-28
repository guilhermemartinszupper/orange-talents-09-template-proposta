package br.com.zupedu.gui.desafioproposta.commons;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Base64;

public class ValidaBiometriaValidator implements ConstraintValidator<ValidaBiometria, String> {

    @Override
    public void initialize(ValidaBiometria constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Base64.getDecoder().decode(s);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
