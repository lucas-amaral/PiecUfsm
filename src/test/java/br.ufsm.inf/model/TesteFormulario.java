package br.ufsm.inf.model;

import br.ufsm.inf.Teste;
import br.ufsm.inf.TestePropriedades;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Lucas on 07/09/2015.
 */
public class TesteFormulario {
    private final SharedDriver webDriver = new SharedDriver();
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    //@Before
    public void setUp() throws Exception {
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Dado("^abrir navegador$")
    public void abrirNavegador() {
//        webDriver = new FirefoxDriver();
    }

    @Dado("^atribuir timeout navegador (\\d+)$")
    public void setTimeoutNavegador(int timeout) {
        webDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }

    @Dado("^acessar endereco (.*)$")
    public void acessarEndereco(String url) {
        webDriver.get(TestePropriedades.urlSistema + url);
    }

    @Quando("^selecionar opcao (.*) no campo (.*) buscando pelo (.*)$")
    public void selecionarOpcaoCampo(String opcao, String campo, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            Select select = new Select(webElement);
            select.selectByVisibleText(opcao);
        }
    }

    @E("^limpe o campo (.*) buscando pelo (.*)$")
    public void limparCampo(String campo, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.clear();
        }
    }

    @E("^preencho campo (.*) com o valor (.*) buscando pelo (.*)$")
    public void preencherCampo(String campo, String valor, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.sendKeys(valor);
        }
    }

    @E("^clicar no elemento (.*) buscando pelo (.*)$")
    public void clicarElemento(String campo, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.click();
        }
    }

    @E("^submeter elemento (.*) buscando pelo (.*)$")
    public void submeterElemento(String campo, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.submit();
        }
    }

    @Entao("^comparar igualdade entre valor esperado (.*) com atributo (.*) do elemento (.*) buscando pelo (.*)$")
    public void compararIgualdade(String valorEsperado, String atributo, String campo, String identificador) {
        assertEquals(valorEsperado, getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^comparar diferença entre valor esperado (.*) com atributo (.*) do elemento (.*) buscando pelo (.*) $")
    public void compararDiferenca(String valorEsperado, String atributo, String campo, String identificador) {
        assertEquals(valorEsperado, getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^comparar se atributo (.*) do elemento (.*) buscando pelo (.*) é verdadeiro$")
    public void compararSeVerdadeiro(String atributo, String campo, String identificador) {
        assertTrue((Boolean) getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^comparar se atributo (.*) do elemento (.*) buscando pelo (.*) é falso")
    public void compararSeFalso(String atributo, String campo, String identificador) {
        assertFalse((Boolean) getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^comparar se atributo (.*) do elemento (.*) buscando pelo (.*) é nulo$")
    public void compararSeNulo(String atributo, String campo, String identificador) {
        assertNull(getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^comparar se atributo (.*) do elemento (.*) buscando pelo (.*) não está nulo$")
    public void compararSeNaoNulo(String atributo, String campo, String identificador) {
        assertNotNull(getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Test
    public void testaFormularios() {}

    //@After
    @Entao("^fecha navegador$")
    public void fechaNavegador() throws Exception {
        webDriver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            webDriver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            webDriver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = webDriver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    private Object getValorPropriedadeCampo(WebElement webElement, String atributo) {
        if (atributo.equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_TEXTO)) {
            return webElement.getText();
        } else if (atributo.equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_NOME_TAG)) {
            return webElement.getTagName();
        } else if (atributo.equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_ATRIBUTO)) {
            return null;
        } else if (atributo.equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_VALOR_CSS)) {
            return null;
        } else if (atributo.equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_CAMPO_EXIBIDO)) {
            return webElement.isDisplayed();
        } else if (atributo.equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_CAMPO_SELECIONADO)) {
            return webElement.isSelected();
        } else if (atributo.equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_CAMPO_HABILITADO)) {
            return webElement.isEnabled();
        } else if (atributo.equals(TestePropriedades.ATRIBUTO_COMPARACAO_URL)) {
            return webDriver.getCurrentUrl();
        }
        return null;
    }

    private WebElement getEncontraCampo(String identificador, String campo) {
        if (identificador.equals(TestePropriedades.IDENTIFICADOR_ID)) {
            return webDriver.findElement(By.id(campo));
        } else if (identificador.equals(TestePropriedades.IDENTIFICADOR_NOME)) {
            return webDriver.findElement(By.name(campo));
        } else if (identificador.equals(TestePropriedades.IDENTIFICADOR_NOME_CLASSE)) {
            return webDriver.findElement(By.className(campo));
        } else if (identificador.equals(TestePropriedades.IDENTIFICADOR_CSS)) {
            return webDriver.findElement(By.cssSelector(campo));
        } else if (identificador.equals(TestePropriedades.IDENTIFICADOR_XPATH)) {
            return webDriver.findElement(By.xpath(campo));
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
            Teste anotacao = TestePropriedades.teste(metodo);
            if (anotacao != null) {
                return anotacao.getValor();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
