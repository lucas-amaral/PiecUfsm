package br.ufsm.inf.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Lucas on 04/09/2015.
 */
public class LoginTeste {
    public static WebDriver loginSucesso() {
        WebDriver webDriver = new FirefoxDriver();
        String url = "http://www.megatecnologia-si.com.br/piec/";
        webDriver.get(url);
        webDriver.findElement(By.id("login")).sendKeys("colegiado");
        webDriver.findElement(By.cssSelector("button.btn.btn-default")).click();
        return webDriver;
    }
}
