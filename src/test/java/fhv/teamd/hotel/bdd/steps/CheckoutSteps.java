package fhv.teamd.hotel.bdd.steps;

import fhv.teamd.hotel.application.FrontDeskService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest
public class CheckoutSteps {

    @Autowired
    FrontDeskService frontDeskService;

    @Given("An active hotel stay with no unassigned payments")
    public void asdf() {

    }

    @When("The check-out procedure is completed")
    public void jkl() {

    }

    @Then("The hotel stay should end and the room be marked as free")
    public void _123() {

    }

}
