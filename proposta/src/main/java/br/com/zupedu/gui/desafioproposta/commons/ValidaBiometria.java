package br.com.zupedu.gui.desafioproposta.commons;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ValidaBiometriaValidator.class})
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidaBiometria {
    String message() default "Biometria Invalida, deve estar no formato base64";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
