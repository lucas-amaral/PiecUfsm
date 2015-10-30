package br.ufsm.inf.model;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created by Lucas on 02/10/2015.
 */
@RunWith(Cucumber.class)
@CucumberOptions (
        format = {"pretty", "html:target/cucumber"},
        features = "src/test/features"
)
public class TesteRunner {
}