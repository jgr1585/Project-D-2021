package fhv.teamd.hotel.bdd.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/",
        glue = { "fhv.teamd.hotel.bdd" },
        plugin = { "pretty", "html:build/cucumber/hotel.html" })
public class CucumberTestRunner {
    @Test
    public void dummyTest() {
        // gradle won't execute otherwise
    }
}