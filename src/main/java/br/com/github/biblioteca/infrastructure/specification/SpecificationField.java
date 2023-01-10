package br.com.github.biblioteca.infrastructure.specification;


import br.com.github.biblioteca.infrastructure.persistence.SpecificationOperation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ FIELD })
public @interface SpecificationField {

    String property() default "";
    
    String join() default "";

    SpecificationOperation operation() default SpecificationOperation.EQUAL;

}
