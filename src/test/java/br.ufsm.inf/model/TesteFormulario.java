package br.ufsm.inf.model;

import br.ufsm.inf.Teste;
import br.ufsm.inf.TestePropriedades;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 07/09/2015.
 */
public class TesteFormulario {
    private WebDriver webDriver;

    @Before
    public void setUp() throws Exception {
        webDriver = LoginTeste.login(TestePropriedades.urlSistema);
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testaFormularios() {
        for (Class classe : getCarregaClasses()) {
            Teste testeClasse = TestePropriedades.teste(classe);
            if (!testeClasse.getUrl().equals("")) {
                webDriver.get(TestePropriedades.urlSistema + testeClasse.getUrl());
            }
            for(Method metodo: classe.getDeclaredMethods()) {
                Teste teste = TestePropriedades.teste(metodo);
                if (teste != null) {
                    setValorCampo(teste);
                }
            }
            WebElement webElementClasse = encontraCampo(testeClasse.getIdentificador(), testeClasse.getCampo());
            if (webElementClasse != null) {
                webElementClasse.click();
                if (testeClasse.getAssert()) {
                    WebElement webElementAssert = encontraCampo(testeClasse.getIdentificadorAssert(), testeClasse.getCampoAssert());
                    assertEquals(testeClasse.getValor(), webElementAssert.getText());
                    System.out.println("Formulário " + testeClasse.getUrl() + " testado!");
                }
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        webDriver.quit();
    }

    public void setValorCampo(Teste teste) {
        WebElement webElement = encontraCampo(teste.getIdentificador(), teste.getCampo());
        if (webElement != null) {
            executaAcoes(teste, webElement);
        }
    }

    private void executaAcoes(Teste teste, WebElement webElement) {
        if (!teste.getUrl().equals("")) {
            webDriver.get(TestePropriedades.urlSistema + teste.getUrl());
        }
        if (teste.limpar()) {
            webElement.clear();
        }
        if (teste.isSelect()) {
            Select select = new Select(webElement);
            if (!teste.getValor().equals("")) {
                select.selectByVisibleText(teste.getValor());
            }
        } else if (!teste.getValor().equals("")) {
            webElement.sendKeys(teste.getValor());
        }
        if (teste.click()) {
            webElement.click();
        }
        if (teste.submit()) {
            webElement.submit();
        }
    }

    private WebElement encontraCampo(String identificador, String campo) {
        if (identificador.equals(TestePropriedades.IDENTIFICADOR_ID)) {
            return webDriver.findElement(By.id(campo));
        } else if (identificador.equals(TestePropriedades.IDENTIFICADOR_NOME)) {
            return webDriver.findElement(By.name(campo));
        } else if (identificador.equals(TestePropriedades.IDENTIFICADOR_NOME_CLASSE)) {
            return webDriver.findElement(By.className(campo));
        } else if (identificador.equals(TestePropriedades.IDENTIFICADOR_CSS)) {
            return webDriver.findElement(By.cssSelector(campo));
        }
        return null;
    }

    public List<Class> getCarregaClasses() {
        File diretorio = new File(TestePropriedades.diretorioModelo);
        List<Class> classes = new ArrayList<Class>();
        populaClasses(classes, diretorio);
        return classes;
    }

    private void populaClasses(List<Class> classes, File no) {
        try {
            if (no.isDirectory()) {
                File[] nos = no.listFiles();
                for (File noAtual : nos) {
                    if (noAtual.isDirectory() || !noAtual.getName().endsWith(".java")) {
                        continue;
                    }
                    String className = noAtual.getAbsolutePath().substring(0, noAtual.getAbsolutePath().length() - 5).replace("\\", ".").split("java.")[1]
                    Class classeAtual = Class.forName(className);
                    Teste teste = TestePropriedades.teste(classeAtual);
                    if (teste != null) {
                        classes.add(classeAtual);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
