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

import static org.junit.Assert.*;

/**
 * Created by Lucas on 07/09/2015.
 */
public class TesteFormulario {
    private WebDriver webDriver;

    @Before
    public void setUp() throws Exception {
        webDriver = LoginTeste.login(TestePropriedades.urlSistema, "", "colegiado");
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testaFormularios() {
        for (Class classe : getCarregaClasses()) {
            Teste testeClasse = TestePropriedades.teste(classe);
            if (testeClasse.fazerLogin()) {
                LoginTeste.login(TestePropriedades.urlSistema, testeClasse.getSenha(), testeClasse.getLogin());
            }
            if (!testeClasse.getUrl().equals("")) {
                webDriver.get(TestePropriedades.urlSistema + testeClasse.getUrl());
            }
            for(Method metodo: classe.getDeclaredMethods()) {
                Teste teste = TestePropriedades.teste(metodo);
                if (teste != null) {
                    executaTeste(teste, true);
                }
            }
            executaTeste(testeClasse, false);
            System.out.println("Formulário " + testeClasse.getUrl() + " testado!");
        }
    }

    @After
    public void tearDown() throws Exception {
        webDriver.quit();
    }

    public void executaTeste(Teste teste, Boolean submeteUrl) {
        WebElement webElement = getEncontraCampo(teste.getIdentificador(), teste.getCampo());
        if (webElement != null) {
            executaAcoes(teste, webElement, submeteUrl);
        }
    }

    private void executaAcoes(Teste teste, WebElement webElement, Boolean submeteUrl) {
        if (!teste.getUrl().equals("") && submeteUrl) {
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
        if (!teste.getCampoAssert().equals("")) {
            executaComparacao(teste);
        }
    }

    private void executaComparacao(Teste teste) {
        WebElement webElementAssert = getEncontraCampo(teste.getIdentificadorAssert(), teste.getCampoAssert());
        if (teste.getTipoAssert().equals(TestePropriedades.TIPO_ASSERT_IGUAL) && !teste.getValorEsperadoAssert().equals("")) {
            assertEquals(teste.getValorEsperadoAssert(), getValorPropriedadeCampo(webElementAssert, teste));
        } else if (teste.getTipoAssert().equals(TestePropriedades.TIPO_ASSERT_DIFERENTE) && !teste.getValorEsperadoAssert().equals("")) {
            assertNotEquals(teste.getValorEsperadoAssert(), getValorPropriedadeCampo(webElementAssert, teste));
        } else if (teste.getTipoAssert().equals(TestePropriedades.TIPO_ASSERT_COLECAO_IGUAL) && !teste.getValorEsperadoAssert().equals("")) {
            //todo: preciso de um objeto e não string
        } else if (teste.getTipoAssert().equals(TestePropriedades.TIPO_ASSERT_VERDADEIRO)) {
            if (!teste.getValorEsperadoAssert().equals("")) {
                assertTrue(teste.getValorEsperadoAssert(), (Boolean) getValorPropriedadeCampo(webElementAssert, teste));
            } else {
                assertTrue((Boolean) getValorPropriedadeCampo(webElementAssert, teste));
            }
        } else if (teste.getTipoAssert().equals(TestePropriedades.TIPO_ASSERT_FALSO)) {
            if (!teste.getValorEsperadoAssert().equals("")) {
                assertFalse(teste.getValorEsperadoAssert(), (Boolean) getValorPropriedadeCampo(webElementAssert, teste));
            } else {
                assertFalse((Boolean) getValorPropriedadeCampo(webElementAssert, teste));
            }
        } else if (teste.getTipoAssert().equals(TestePropriedades.TIPO_ASSERT_NULO)) {
            if (!teste.getValorEsperadoAssert().equals("")) {
                assertNull(teste.getValorEsperadoAssert(), getValorPropriedadeCampo(webElementAssert, teste));
            } else {
                assertNull(getValorPropriedadeCampo(webElementAssert, teste));
            }
        } else if (teste.getTipoAssert().equals(TestePropriedades.TIPO_ASSERT_NAO_NULO)) {
            if (!teste.getValorEsperadoAssert().equals("")) {
                assertNotNull(teste.getValorEsperadoAssert(), getValorPropriedadeCampo(webElementAssert, teste));
            } else {
                assertNotNull(getValorPropriedadeCampo(webElementAssert, teste));
            }
        }
    }

    private Object getValorPropriedadeCampo(WebElement webElement, Teste teste) {
        if (teste.getAtributoCampoComparacaoAssert().equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_TEXTO)) {
            return webElement.getText();
        } else if (teste.getAtributoCampoComparacaoAssert().equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_NOME_TAG)) {
            return webElement.getTagName();
        } else if (teste.getAtributoCampoComparacaoAssert().equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_ATRIBUTO)) {
            return null;
        } else if (teste.getAtributoCampoComparacaoAssert().equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_VALOR_CSS)) {
            return null;
        } else if (teste.getAtributoCampoComparacaoAssert().equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_CAMPO_EXIBIDO)) {
            return webElement.isDisplayed();
        } else if (teste.getAtributoCampoComparacaoAssert().equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_CAMPO_SELECIONADO)) {
            return webElement.isSelected();
        } else if (teste.getAtributoCampoComparacaoAssert().equals(TestePropriedades.ATRIBUTO_COMPARACAO_ASSERT_CAMPO_HABILITADO)) {
            return webElement.isEnabled();
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
                    String className = noAtual.getAbsolutePath().substring(0, noAtual.getAbsolutePath().length() - 5).replace("\\", ".").split("java.")[1];
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
