package br.ufsm.inf.model;

import br.ufsm.inf.TestePropriedades;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Lucas on 07/09/2015.
 */
public class TesteFormulario {
    public static final String IDENTIFICADOR_ID = "id";
    public static final String IDENTIFICADOR_NOME = "nome";
    public static final String IDENTIFICADOR_NOME_CLASSE = "nome da classe";
    public static final String IDENTIFICADOR_CSS = "css";
    public static final String IDENTIFICADOR_XPATH = "xpath";
    public static final String IDENTIFICADOR_TEXTO_DO_LINK = "Texto do link";
    public static final String IDENTIFICADOR_NOME_TAG = "Nome tag";

    public static final String ATRIBUTO_COMPARACAO_ASSERT_TEXTO = "texto";
    public static final String ATRIBUTO_COMPARACAO_ASSERT_NOME_TAG = "nome tag";
    public static final String ATRIBUTO_COMPARACAO_ASSERT_ATRIBUTO = "atributo";
    public static final String ATRIBUTO_COMPARACAO_ASSERT_VALOR_CSS = "valor css";
    public static final String ATRIBUTO_COMPARACAO_ASSERT_CAMPO_HABILITADO = "campo habilitado";
    public static final String ATRIBUTO_COMPARACAO_ASSERT_CAMPO_SELECIONADO = "campo selecionado";
    public static final String ATRIBUTO_COMPARACAO_ASSERT_CAMPO_EXIBIDO = "campo exibido";
    public static final String ATRIBUTO_COMPARACAO_URL = "url";
    public static final String ATRIBUTO_COMPARACAO_TITULO_PAGINA = "titulo da pagina";

    private final SharedDriver webDriver;

    public TesteFormulario(SharedDriver webDriver) {
        this.webDriver = webDriver;
    }

    public Object getValorPropriedadeCampo(WebElement webElement, String atributo, String valorAtributo) {
        if (atributo.equals(ATRIBUTO_COMPARACAO_ASSERT_TEXTO)) {
            return webElement.getText();
        } else if (atributo.equals(ATRIBUTO_COMPARACAO_ASSERT_NOME_TAG)) {
            return webElement.getTagName();
        } else if (atributo.equals(ATRIBUTO_COMPARACAO_ASSERT_ATRIBUTO)) {
            return webElement.getAttribute(valorAtributo);
        } else if (atributo.equals(ATRIBUTO_COMPARACAO_ASSERT_VALOR_CSS)) {
            return webElement.getCssValue(valorAtributo);
        } else if (atributo.equals(ATRIBUTO_COMPARACAO_ASSERT_CAMPO_EXIBIDO)) {
            return webElement.isDisplayed();
        } else if (atributo.equals(ATRIBUTO_COMPARACAO_ASSERT_CAMPO_SELECIONADO)) {
            return webElement.isSelected();
        } else if (atributo.equals(ATRIBUTO_COMPARACAO_ASSERT_CAMPO_HABILITADO)) {
            return webElement.isEnabled();
        } else if (atributo.equals(ATRIBUTO_COMPARACAO_URL)) {
            return webDriver.getCurrentUrl();
        } else if (atributo.equals(ATRIBUTO_COMPARACAO_TITULO_PAGINA)) {
            return webDriver.getTitle();
        }
        return null;
    }

    public WebElement getEncontraCampo(String identificador, String campo) {
        return webDriver.findElement(getEncontra(identificador, campo));
    }

    public By getEncontra(String identificador, String campo) {
        if (identificador.equals(IDENTIFICADOR_ID)) {
            return By.id(campo);
        } else if (identificador.equals(IDENTIFICADOR_NOME)) {
            return By.name(campo);
        } else if (identificador.equals(IDENTIFICADOR_NOME_CLASSE)) {
            return By.className(campo);
        } else if (identificador.equals(IDENTIFICADOR_CSS)) {
            return By.cssSelector(campo);
        } else if (identificador.equals(IDENTIFICADOR_XPATH)) {
            return By.xpath(campo);
        } else if (identificador.equals(IDENTIFICADOR_TEXTO_DO_LINK)) {
            return By.linkText(campo);
        } else if (identificador.equals(IDENTIFICADOR_NOME_TAG)) {
            return By.tagName(campo);
        }
        return null;
    }
}
