package br.ufsm.inf.model;

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

    public static WebDriver login(String url, String senha, String login, WebDriver webDriver) {
        webDriver.get(url);
        webDriver.findElement(By.id("login")).sendKeys(login);
        webDriver.findElement(By.cssSelector("button.btn.btn-default")).click();
        return webDriver;
    }
}
