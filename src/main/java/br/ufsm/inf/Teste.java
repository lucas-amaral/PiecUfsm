package br.ufsm.inf;

import java.lang.annotation.*;

/**
 * Created by Lucas on 07/09/2015.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Teste {
    String getTipo() default "input";
    String getValor() default "";
    String getUrl() default "";
    String getIdentificador() default "id";
    String getCampo() default "";

    boolean getAssert() default false;
    String getIdentificadorAssert() default "id";
    String getCampoAssert() default "";
}
