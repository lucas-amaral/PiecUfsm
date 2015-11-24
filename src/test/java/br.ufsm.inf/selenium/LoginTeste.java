package br.ufsm.inf.selenium;

import br.ufsm.inf.model.SharedDriver;
import br.ufsm.inf.model.Steps;
import br.ufsm.inf.model.TesteFormulario;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Lucas on 04/09/2015.
 */
public class LoginTeste {
    public static WebDriver login(String url, String senha, String login) {
        WebDriver webDriver = new FirefoxDriver();
        webDriver.get(url);
        webDriver.findElement(By.id("login")).sendKeys(login);
        webDriver.findElement(By.cssSelector("button.btn.btn-default")).click();
        return webDriver;
    }

    private Steps steps = new Steps();

        @Test
        /*Fazer login no sistema como um membro do colegiado*/
        public void getLoginMembroColegiado() {
            steps.acessarEndereco("/login.htm");
            steps.preencherCampo("login", "colegiado", TesteFormulario.IDENTIFICADOR_ID);
            steps.preencherCampo("senha", "colegiado123", TesteFormulario.IDENTIFICADOR_ID);
            steps.clicarElemento("button.btn-default", TesteFormulario.IDENTIFICADOR_CSS);
            steps.compararSeNaoNulo(TesteFormulario.ATRIBUTO_COMPARACAO_ASSERT_NOME_TAG, "id", TesteFormulario.IDENTIFICADOR_NOME);
        }

    public static WebDriver login(String url, String senha, String login, WebDriver webDriver) {
        webDriver.get(url);
        webDriver.findElement(By.id("login")).sendKeys(login);
        webDriver.findElement(By.cssSelector("button.btn.btn-default")).click();
        return webDriver;
    }
}
