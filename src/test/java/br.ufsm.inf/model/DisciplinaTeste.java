package br.ufsm.inf.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by Lucas on 27/08/2015.
 */
public class DisciplinaTeste {
    private WebDriver webDriver;
    private String url;
    //todo: seria interessante inserir service aqui, para buscar infos no bd

    @Before
    public void setUp() throws Exception {
        webDriver = new FirefoxDriver();
        //todo: é necessário fazer login?
        url = "http://www.megatecnologia-si.com.br/piec/";
        webDriver.get(url);
        webDriver.findElement(By.id("login")).sendKeys("colegiado");
        webDriver.findElement(By.cssSelector("button.btn.btn-default")).click();
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void cadastrarDisciplinaSucesso() {
        webDriver.get(url + "cadastro-disciplina.htm");
        webDriver.findElement(By.id("codigo")).sendKeys("ELC9999"); //input
        webDriver.findElement(By.id("nome")).sendKeys("Teste de Disciplina"); //input
        Select cargaHoraria = new Select(webDriver.findElement(By.id("cargaHoraria"))); //select
        cargaHoraria.selectByVisibleText("60");
        webDriver.findElement(By.id("ativa1")).click(); //checkbox
        //todo: alterar para buscar uma instituição cadastrada no banco de dados
        Select instituicao = new Select(webDriver.findElement(By.id("idInstituicao")));
        instituicao.selectByVisibleText("UFSM - Universidade Federal de Santa Maria");
        webDriver.findElement(By.id("preAprovada1")).click();
        webDriver.findElement(By.id("salvar")).click();
        assertEquals("Sucesso!", webDriver.findElement(By.cssSelector("h4")).getText());
    }

    //@Test
    public void cadastrarDisciplinaErro() {
        webDriver.get(url + "/cadastro-disciplina.htm");
        webDriver.findElement(By.id("codigo")).sendKeys("ELC139"); //Programação paralela
        webDriver.findElement(By.id("salvar")).click();
//        assertEquals("Código já cadastrado em outra disciplina.", webDriver.findElement(By.id("disciplina.errors")).getText());
    }

//    @Test
//    public void excluirDisciplinaErro() {
//
//    }
//
//    @Test
//    public void excluirDisciplinaSucesso() {
//
//    }


    @After
    public void tearDown() throws Exception {
        webDriver.quit();
    }
}
