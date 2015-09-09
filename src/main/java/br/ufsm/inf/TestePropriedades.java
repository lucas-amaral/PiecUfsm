package br.ufsm.inf;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Lucas on 08/09/2015.
 */
public class TestePropriedades {
    public static final String TIPO_INPUT = "input";
    public static final String TIPO_SELECT = "select";
    public static final String TIPO_CHECKBOX = "checkbox";
    public static final String TIPO_RADIOBUTTON = "radiobutton";

    public static final String IDENTIFICADOR_ID = "id";
    public static final String IDENTIFICADOR_NOME = "nome";
    public static final String IDENTIFICADOR_NOME_CLASSE = "nome da classe";
    public static final String IDENTIFICADOR_CSS = "css";


    public static final String diretorioModelo = "D:\\Projetos\\1.7\\PiecUfsm\\src\\main\\java\\br\\ufsm\\inf\\model";
    public static final String pacoteModelo = "br.ufsm.inf.model.";

    public static final String urlSistema = "http://localhost";

    public static Teste teste(Class classe) {
        return (Teste) anotacao(classe);
    }

    public static Teste teste(Method metodo) {
        return (Teste) anotacao(metodo);
    }

    public static Object anotacao(Method metodo) {
        for (Annotation annotation : metodo.getAnnotations()) {
            if (Teste.class.isAssignableFrom(annotation.getClass())) {
                return annotation;
            }
        }
        return null;
    }

    public static Object anotacao(Class classe) {
        for (Annotation annotation : classe.getAnnotations()) {
            if (Teste.class.isAssignableFrom(annotation.getClass())) {
                return annotation;
            }
        }
        return null;
    }

}
