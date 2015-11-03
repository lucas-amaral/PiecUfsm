package br.ufsm.inf.model;

import br.ufsm.inf.TestePropriedades;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import org.junit.Test;
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

    private final SharedDriver webDriver = new SharedDriver();

    //@Before
    public void setUp() throws Exception {
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Dado("^abro o navegador$")
    public void abrirNavegador() {
//        webDriver = new FirefoxDriver();
    }

    @Dado("^atribuir timeout navegador (\\d+)$")
    public void setTimeoutNavegador(int timeout) {
        webDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }

    @Dado("^acesso o endereco (.*)$")
    public void acessarEndereco(String url) {
        webDriver.get(TestePropriedades.urlSistema + url);
    }

    @Quando("^seleciono a opcao (.*) no campo (.*) buscando pelo (.*)$")
    public void selecionarOpcaoCampo(String opcao, String campo, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            Select select = new Select(webElement);
            select.selectByVisibleText(opcao);
        }
    }

    @E("^limpo o campo (.*) buscando pelo (.*)$")
    public void limparCampo(String campo, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.clear();
        }
    }

    @E("^preencho o campo (.*) com o valor (.*) buscando pelo (.*)$")
    public void preencherCampo(String campo, String valor, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.sendKeys(valor);
        }
    }

    @E("^clico no elemento (.*) buscando pelo (.*)$")
    public void clicarElemento(String campo, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.click();
        }
    }

    @E("^submeto o elemento (.*) buscado pelo (.*)$")
    public void submeterElemento(String campo, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.submit();
        }
    }

    @E("^aguardo (\\d+) milisegundos para verificar se elemento (.*) buscando pelo (.*) esta presente$")
    public void aguardarCampoExistente(Long tempo, String campo, String identificador) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, tempo);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(getEncontra(identificador, campo)));
    }

    @E("^aguardo (\\d+) milisegundos para verificar se elemento (.*) buscando pelo (.*) esta visivel$")
    public void aguardarCampoVisivel(Long tempo, String campo, String identificador) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, tempo);
        webDriverWait.until(ExpectedConditions.visibilityOf(getEncontraCampo(identificador, campo)));
    }

    @E("^aguardo (\\d+) milisegundos para verificar se elemento (.*) buscando pelo (.*) desapareceu$")
    public void aguardarCampoDesaparecer(Long tempo, String campo, String identificador) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, tempo);
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(getEncontra(identificador, campo)));
    }

    @Entao("^comparo a igualdade entre o valor esperado (.*) com atributo (.*) do elemento (.*) buscando pelo (.*)$")
    public void compararIgualdade(String valorEsperado, String atributo, String campo, String identificador) {
        assertEquals(valorEsperado, getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^comparo a diferença entre valor esperado (.*) com atributo (.*) do elemento (.*) buscando pelo (.*) $")
    public void compararDiferenca(String valorEsperado, String atributo, String campo, String identificador) {
        assertNotEquals(valorEsperado, getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^verifico se atributo (.*) do elemento (.*) buscando pelo (.*) é verdadeiro$")
    public void compararSeVerdadeiro(String atributo, String campo, String identificador) {
        assertTrue((Boolean) getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^verifico se atributo (.*) do elemento (.*) buscando pelo (.*) é falso")
    public void compararSeFalso(String atributo, String campo, String identificador) {
        assertFalse((Boolean) getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^verifico se atributo (.*) do elemento (.*) buscando pelo (.*) é nulo$")
    public void compararSeNulo(String atributo, String campo, String identificador) {
        assertNull(getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^verifico se atributo (.*) do elemento (.*) buscando pelo (.*) não está nulo$")
    public void compararSeNaoNulo(String atributo, String campo, String identificador) {
        assertNotNull(getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Test
    public void testaFormularios() {}

    //@After
    @Entao("^fecha navegador$")
    public void fechaNavegador() throws Exception {
        webDriver.quit();
    }

    private Object getValorPropriedadeCampo(WebElement webElement, String atributo) {
        if (atributo.equals(Steps.ATRIBUTO_COMPARACAO_ASSERT_TEXTO)) {
            return webElement.getText();
        } else if (atributo.equals(Steps.ATRIBUTO_COMPARACAO_ASSERT_NOME_TAG)) {
            return webElement.getTagName();
        } else if (atributo.equals(Steps.ATRIBUTO_COMPARACAO_ASSERT_ATRIBUTO)) {
            return null;
        } else if (atributo.equals(Steps.ATRIBUTO_COMPARACAO_ASSERT_VALOR_CSS)) {
            return null;
        } else if (atributo.equals(Steps.ATRIBUTO_COMPARACAO_ASSERT_CAMPO_EXIBIDO)) {
            return webElement.isDisplayed();
        } else if (atributo.equals(Steps.ATRIBUTO_COMPARACAO_ASSERT_CAMPO_SELECIONADO)) {
            return webElement.isSelected();
        } else if (atributo.equals(Steps.ATRIBUTO_COMPARACAO_ASSERT_CAMPO_HABILITADO)) {
            return webElement.isEnabled();
        } else if (atributo.equals(Steps.ATRIBUTO_COMPARACAO_URL)) {
            return webDriver.getCurrentUrl();
        }
        return null;
    }

    private WebElement getEncontraCampo(String identificador, String campo) {
        return webDriver.findElement(getEncontra(identificador, campo));
    }

    public By getEncontra(String identificador, String campo) {
        if (identificador.equals(Steps.IDENTIFICADOR_ID)) {
            return By.id(campo);
        } else if (identificador.equals(Steps.IDENTIFICADOR_NOME)) {
            return By.name(campo);
        } else if (identificador.equals(Steps.IDENTIFICADOR_NOME_CLASSE)) {
            return By.className(campo);
        } else if (identificador.equals(Steps.IDENTIFICADOR_CSS)) {
            return By.cssSelector(campo);
        } else if (identificador.equals(Steps.IDENTIFICADOR_XPATH)) {
            return By.xpath(campo);
        } else if (identificador.equals(Steps.IDENTIFICADOR_TEXTO_DO_LINK)) {
            return By.linkText(campo);
        } else if (identificador.equals(Steps.IDENTIFICADOR_NOME_TAG)) {
            return By.tagName(campo);
        }
        return null;
    }
}
