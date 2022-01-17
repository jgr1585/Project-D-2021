package fhv.teamd.hotel.bdd.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/",
        glue = { "at.fhv.se.banking.bdd" },
        plugin = { "pretty", "html:build/cucumber/hotel.html" })
public class CucumberTestRunner {
}