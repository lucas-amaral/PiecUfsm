package br.ufsm.inf;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Lucas on 08/09/2015.
 */
public class TestePropriedades {
    public static final String IDENTIFICADOR_ID = "id";
    public static final String IDENTIFICADOR_NOME = "nome";
    public static final String IDENTIFICADOR_NOME_CLASSE = "nome da classe";
    public static final String IDENTIFICADOR_CSS = "css";

    public static final String TIPO_ASSERT_IGUAL = "igual";
    public static final String TIPO_ASSERT_DIFERENTE = "diferente";
    public static final String TIPO_ASSERT_NULO = "nulo";
    public static final String TIPO_ASSERT_NAO_NULO = "nao nulo";
    public static final String TIPO_ASSERT_FALSO = "falso";
    public static final String TIPO_ASSERT_VERDADEIRO = "verdadeiro";
    public static final String TIPO_ASSERT_COLECAO_IGUAL = "colecao igual";

    public static final String ATRIBUTO_COMPARACAO_ASSERT_TEXTO = "texto";
    public static final String ATRIBUTO_COMPARACAO_ASSERT_NOME_TAG = "nome tag";
    public static final String ATRIBUTO_COMPARACAO_ASSERT_ATRIBUTO = "atributo";
    public static final String ATRIBUTO_COMPARACAO_ASSERT_VALOR_CSS = "valor css";
    public static final String ATRIBUTO_COMPARACAO_ASSERT_CAMPO_HABILITADO = "campo habilitado";
    public static final String ATRIBUTO_COMPARACAO_ASSERT_CAMPO_SELECIONADO = "campo selecionado";
    public static final String ATRIBUTO_COMPARACAO_ASSERT_CAMPO_EXIBIDO = "campo exibido";


    public static final String diretorioModelo = "E:\\Dados\\Projetos\\1.7\\PiecUfsm\\src\\main\\java\\br\\ufsm\\inf\\model";

    public static final String urlSistema = "http://www.megatecnologia-si.com.br/piec";

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
