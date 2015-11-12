package br.ufsm.inf;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Lucas on 08/09/2015.
 */
public class TestePropriedades {
    public static final String IDENTIFICADOR_ID = "id";
    public static final String IDENTIFICADOR_NOME = "nome";
    public static final String IDENTIFICADOR_NOME_CLASSE = "nome da classe";
    public static final String IDENTIFICADOR_CSS = "css";
    public static final String IDENTIFICADOR_XPATH = "xpath";

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
    public static final String ATRIBUTO_COMPARACAO_URL = "url";
    public static final String ATRIBUTO_COMPARACAO_TITULO_PAGINA = "titulo da pagina";

    public static final String urlSistema = "localhost";

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

    public static Method getMetodo(Class classe, String propriedade) throws NoSuchMethodException {
        propriedade = propriedade.replaceAll("_", "");
        Method retorno;
        for (int pontos = propriedade.indexOf(".") ; pontos > -1 ; pontos = propriedade.indexOf(".")) {
            String propriedadeMetodo = propriedade.substring(0, pontos);
            propriedade = propriedade.substring(pontos + 1);
            retorno = getMetodo(classe, propriedadeMetodo);
            classe = retorno.getReturnType();
            if (retorno.getGenericReturnType() instanceof ParameterizedType) {
                ParameterizedType tipoMetodo = (ParameterizedType) retorno.getGenericReturnType();
                if (tipoMetodo.getActualTypeArguments().length == 1 && tipoMetodo.getActualTypeArguments()[0].toString().contains("silas")) {
                    classe = (Class) tipoMetodo.getActualTypeArguments()[0];
                } else if (tipoMetodo.getActualTypeArguments().length == 2 && tipoMetodo.getActualTypeArguments()[1].toString().contains("silas")) {
                    classe = (Class) tipoMetodo.getActualTypeArguments()[1];
                }
            }
        }
        if (propriedade.contains("[")) {
            String nomeProp = propriedade.substring(0, propriedade.indexOf("["));
            retorno = classe.getMethod("get" + nomeProp.substring(0, 1).toUpperCase() + nomeProp.substring(1));
        } else {
            retorno = classe.getMethod("get" + propriedade.substring(0, 1).toUpperCase() + propriedade.substring(1));
        }
        return retorno;
    }

    public static String getValorPadraoMetodo(Class classe, String propriedade) {
        try {
            Method metodo = getMetodo(classe, propriedade);
            Teste anotacao = teste(metodo);
            if (anotacao != null) {
                return anotacao.getValor();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

}
