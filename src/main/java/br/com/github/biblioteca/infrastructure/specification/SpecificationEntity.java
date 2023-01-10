package br.com.github.biblioteca.infrastructure.specification;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ TYPE })
public @interface SpecificationEntity {

    Class<?> value();

}
