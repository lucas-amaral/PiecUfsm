package br.ufsm.inf.model;

import br.ufsm.inf.TestePropriedades;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Lucas on 07/09/2015.
 */
public class Steps {
    private final SharedDriver webDriver = new SharedDriver();
    private TesteFormulario funcoes = new TesteFormulario(webDriver);

    @Dado("^atribuir timeout navegador (\\d+)$")
    public void setTimeoutNavegador(int timeout) {
        webDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }

    @Dado("^acesso o endereco (.*)$")
    public void acessarEndereco(String url) {
        webDriver.get(TestePropriedades.urlSistema + url);
    }

    @Quando("^seleciono a opcao (.*) no campo (.*)$")
    public WebElement selecionarOpcaoCampo(String opcao, String campo) {
        return selecionarOpcaoCampo(opcao, campo, TesteFormulario.IDENTIFICADOR_ID);
    }

    @Quando("^seleciono a opcao (.*) no campo (.*) buscando pelo (.*)$")
    public WebElement selecionarOpcaoCampo(String opcao, String campo, String identificador) {
        WebElement webElement = funcoes.getEncontraCampo(identificador, campo);
        if (webElement != null) {
            Select select = new Select(webElement);
            select.selectByVisibleText(opcao);
        }
        return webElement;
    }

    @E("^limpo o campo (.*)$")
    public WebElement limparCampo(String campo) {
        return limparCampo(campo, TesteFormulario.IDENTIFICADOR_ID);
    }

    @E("^limpo o campo (.*) buscando pelo (.*)$")
    public WebElement limparCampo(String campo, String identificador) {
        WebElement webElement = funcoes.getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.clear();
        }
        return webElement;
    }

    @E("^preencho o campo (.*) com o valor (.*)$")
    public WebElement preencherCampo(String campo, String valor) {
        return preencherCampo(campo, valor, TesteFormulario.IDENTIFICADOR_ID);
    }

    @E("^preencho o campo (.*) com o valor (.*) buscando pelo (.*)$")
    public WebElement preencherCampo(String campo, String valor, String identificador) {
        WebElement webElement = funcoes.getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.sendKeys(valor);
        }
        return webElement;
    }

    @E("^clico no elemento (.*)$")
    public WebElement clicarElemento(String campo) {
        return clicarElemento(campo, TesteFormulario.IDENTIFICADOR_ID);
    }

    @E("^clico no elemento (.*) buscando pelo (.*)$")
    public WebElement clicarElemento(String campo, String identificador) {
        WebElement webElement = funcoes.getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.click();
        }
        return webElement;
    }

    @E("^submeto o elemento (.*)$")
    public WebElement submeterElemento(String campo) {
        return submeterElemento(campo, TesteFormulario.IDENTIFICADOR_ID);
    }

    @E("^submeto o elemento (.*) buscado pelo (.*)$")
    public WebElement submeterElemento(String campo, String identificador) {
        WebElement webElement = funcoes.getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.submit();
        }
        return webElement;
    }

    @E("^aguardo (\\d+) milisegundos para verificar se elemento (.*) buscando pelo (.*) esta presente$")
    public void aguardarCampoExistente(Long tempo, String campo, String identificador) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, tempo);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(funcoes.getEncontra(identificador, campo)));
    }

    @E("^aguardo (\\d+) milisegundos para verificar se elemento (.*) buscando pelo (.*) esta visivel$")
    public void aguardarCampoVisivel(Long tempo, String campo, String identificador) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, tempo);
        webDriverWait.until(ExpectedConditions.visibilityOf(funcoes.getEncontraCampo(identificador, campo)));
    }

    @E("^aguardo (\\d+) milisegundos para verificar se elemento (.*) buscando pelo (.*) desapareceu$")
    public void aguardarCampoDesaparecer(Long tempo, String campo, String identificador) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, tempo);
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(funcoes.getEncontra(identificador, campo)));
    }

    @Entao("^comparo a igualdade entre o valor esperado (.*) com atributo (.*) do elemento (.*) buscando pelo (.*)$")
    public void compararIgualdade(String valorEsperado, String atributo, String campo, String identificador) {
        assertEquals(valorEsperado, funcoes.getValorPropriedadeCampo(funcoes.getEncontraCampo(identificador, campo), atributo, null));
    }

    @Entao("^comparo a diferença entre valor esperado (.*) com atributo (.*) do elemento (.*) buscando pelo (.*) $")
    public void compararDiferenca(String valorEsperado, String atributo, String campo, String identificador) {
        assertNotEquals(valorEsperado, funcoes.getValorPropriedadeCampo(funcoes.getEncontraCampo(identificador, campo), atributo, null));
    }

    @Entao("^verifico se atributo (.*) do elemento (.*) buscando pelo (.*) é verdadeiro$")
    public void compararSeVerdadeiro(String atributo, String campo, String identificador) {
        assertTrue((Boolean) funcoes.getValorPropriedadeCampo(funcoes.getEncontraCampo(identificador, campo), atributo, null));
    }

    @Entao("^verifico se atributo (.*) do elemento (.*) buscando pelo (.*) é falso")
    public void compararSeFalso(String atributo, String campo, String identificador) {
        assertFalse((Boolean) funcoes.getValorPropriedadeCampo(funcoes.getEncontraCampo(identificador, campo), atributo, null));
    }

    @Entao("^verifico se atributo (.*) do elemento (.*) buscando pelo (.*) é nulo$")
    public void compararSeNulo(String atributo, String campo, String identificador) {
        assertNull(funcoes.getValorPropriedadeCampo(funcoes.getEncontraCampo(identificador, campo), atributo, null));
    }

    @Entao("^verifico se atributo (.*) do elemento (.*) buscando pelo (.*) não está nulo$")
    public void compararSeNaoNulo(String atributo, String campo, String identificador) {
        assertNotNull(funcoes.getValorPropriedadeCampo(funcoes.getEncontraCampo(identificador, campo), atributo, null));
    }

    @Entao("^fecha navegador$")
    public void fechaNavegador() throws Exception {
        webDriver.quit();
    }
}
