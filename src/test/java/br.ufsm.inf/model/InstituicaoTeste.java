package br.ufsm.inf.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Lucas on 04/09/2015.
 */
public class InstituicaoTeste {
    private WebDriver webDriver;
    private String url;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        url = "http://www.megatecnologia-si.com.br/piec";
        webDriver = LoginTeste.loginSucesso();
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void cadastrarInstituicaoSucesso() throws Exception {
        webDriver.get(url + "/cadastro-disciplina.htm");
        webDriver.findElement(By.linkText("Instituições")).click();
        webDriver.findElement(By.cssSelector("button.btn.btn-success")).click();
        webDriver.findElement(By.id("sigla")).sendKeys("A");
        webDriver.findElement(By.id("nome")).sendKeys("A b c");
        new Select(webDriver.findElement(By.id("estado"))).selectByVisibleText("Alagoas"); //todo: melhorar
        webDriver.findElement(By.cssSelector("input.btn.btn-success")).click(); //Salvar
        assertEquals("Sucesso!", webDriver.findElement(By.cssSelector("h4")).getText());
        excluirInstituicaoSucesso();
    }

    public void excluirInstituicaoSucesso() {
        webDriver.findElement(By.cssSelector("input.btn.btn-danger")).click(); //Excluir
    }

    @After
    public void tearDown() throws Exception {
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
}
