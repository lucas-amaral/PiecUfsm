package br.ufsm.inf.model;

import br.ufsm.inf.TestePropriedades;
import cucumber.api.PendingException;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Lucas on 07/09/2015.
 */
public class TesteFormulario {
    private WebDriver webDriver;

    /* Inicio das regras Cucumber*/

    @Dado("^abrir navegador$")
    public void abrir_navegador() {
        webDriver = new FirefoxDriver();
    }

    @Dado("^acessar endereco (.*)$")
    public void acessar_endereco(String url) {
        webDriver.get(TestePropriedades.urlSistema + url);
    }

    @Quando("^selecionar opcao (.*) no campo (.*) buscando pelo (.*)$")
    public void selecionar_opcao_campo(String opcao, String campo, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            Select select = new Select(webElement);
            select.selectByVisibleText(opcao);
        }
    }

    @E("^limpe o campo (.*) buscando pelo (.*)$")
    public void limpar_campo(String campo, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.clear();
        }
    }

    @E("^preencho campo (.*) com o valor (.*) buscando pelo (.*)$")
    public void preencher_campo(String campo, String valor, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.sendKeys(valor);
        }
    }

    @E("^clicar no elemento (.*) buscando pelo (.*)$")
    public void clicar_elemento(String campo, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.click();
        }
    }

    @E("^submeter elemento (.*) buscando pelo (.*)$")
    public void submeter_elemento(String campo, String identificador) {
        WebElement webElement = getEncontraCampo(identificador, campo);
        if (webElement != null) {
            webElement.submit();
        }
    }

    @Entao("^comparar igualdade entre valor esperado (.*) com atributo (.*) do elemento (.*) buscando pelo (.*)$")
    public void comparar_igualdade(String valorEsperado, String atributo, String campo, String identificador) {
        assertEquals(valorEsperado, getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^comparar diferença entre valor esperado (.*) com atributo (.*) do elemento (.*) buscando pelo (.*) $")
    public void comparar_diferenca(String valorEsperado, String atributo, String campo, String identificador) {
        assertEquals(valorEsperado, getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^comparar se atributo (.*) do elemento (.*) buscando pelo (.*) é verdadeiro$")
    public void comparar_se_verdadeiro(String atributo, String campo, String identificador) {
        assertTrue((Boolean) getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^comparar se atributo (.*) do elemento (.*) buscando pelo (.*) é falso")
    public void comparar_se_falso(String atributo, String campo, String identificador) {
        assertFalse((Boolean) getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^comparar se atributo (.*) do elemento (.*) buscando pelo (.*) é nulo$")
    public void comparar_se_nulo(String atributo, String campo, String identificador) {
        assertNull(getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^comparar se atributo (.*) do elemento (.*) buscando pelo (.*) não está nulo$")
    public void comparar_se_nao_nulo(String atributo, String campo, String identificador) {
        assertNotNull(getValorPropriedadeCampo(getEncontraCampo(identificador, campo), atributo));
    }

    @Entao("^fecha navegador$")
    public void fecha_navegador() {
        webDriver.quit();
    }

    /* Inicio das regras Selenium*/

    @Before
    public void setUp() throws Exception {
        webDriver = LoginTeste.login(TestePropriedades.urlSistema, "", "colegiado");
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testaFormularios() {}

    @After
    public void tearDown() throws Exception {
        webDriver.quit();
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
}
