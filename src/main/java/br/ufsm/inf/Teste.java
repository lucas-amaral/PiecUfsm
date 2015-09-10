package br.ufsm.inf;

import java.lang.annotation.*;

/**
 * Created by Lucas on 07/09/2015.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Teste {
    String getUrl() default "";

    //findElement
    String getCampo() default ""; //campo html do formulário
    String getIdentificador() default "id"; //Informar se deve buscar um id, name, class ou css

    boolean isSelect() default false;

    String getValor() default ""; //utilizado como sendKeys e selectText
    boolean click() default false;
    boolean submit() default false;
    boolean limpar() default false;

    boolean getAssert() default false;
    String getIdentificadorAssert() default "id";
    String getCampoAssert() default "";
}
