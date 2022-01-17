package fhv.teamd.hotel.bdd.steps;

import fhv.teamd.hotel.application.BillingService;
import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.FrontDeskService;
import fhv.teamd.hotel.application.RoomSuggestionService;
import fhv.teamd.hotel.application.dto.RoomDTO;
import fhv.teamd.hotel.application.dto.StayDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.domain.exceptions.AlreadyCheckedOutException;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CucumberContextConfiguration
@SpringBootTest
public class CheckoutSteps {

    @Autowired
    PlatformTransactionManager txManager;

    private TransactionStatus tx;

    @Autowired
    FrontDeskService frontDeskService;

    @Autowired
    BillingService billingService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    RoomSuggestionService roomSuggestionService;

    String stayId;
    List<String> roomIds;

    @Before
    public void beforeScenario() {
        this.tx = this.txManager.getTransaction(new DefaultTransactionDefinition());
    }

    @Given("An active hotel stay with no unassigned payments")
    public void given() throws Exception {

        StayDTO stay = this.frontDeskService.getActiveStays()
                .stream()
                .filter(s -> {
                    try {
                        return this.billingService.getBill(s.getId()).entries().isEmpty();
                    } catch (InvalidIdException e) {
                        // this never happens but won't compile if not caught
                        return false;
                    }
                })
                .findAny()
                .orElseThrow(() -> new Exception("couldn't find any valid test data"));

        this.stayId = stay.getId();
        this.roomIds = stay.rooms().stream().map(RoomDTO::id).collect(Collectors.toList());
    }

    @When("The check-out procedure is completed")
    public void when() throws AlreadyCheckedOutException, InvalidIdException {

        this.frontDeskService.checkOut(this.stayId);
    }

    @Then("The hotel stay should end and the room be marked as free")
    public void then() {

        // Hotel stay should end (no longer active)

        Optional<StayDTO> stay = this.frontDeskService.getActiveStays()
                .stream()
                .filter(s -> s.getId().equals(this.stayId))
                .findFirst();

        Assertions.assertTrue(stay.isEmpty());

        // Rooms should be free

        // AppLayer doesn't have isFree(room)
        // -> query all the currently free rooms and see if the freed rooms are contained

        LocalDateTime from = LocalDateTime.now();
        LocalDateTime until = from.plus(Duration.ofMinutes(10));
        int limit = Integer.MAX_VALUE;

        List<String> freeRoomIds = this.categoryService.getAll()
                .stream()
                .flatMap(cat -> this.roomSuggestionService.findSuitableRooms(cat.id(), from, until, limit).stream())
                .map(RoomDTO::id)
                .collect(Collectors.toList());

        Assertions.assertTrue(freeRoomIds.containsAll(this.roomIds));
    }

    @After
    public void afterScenario() {
        this.txManager.rollback(this.tx);
    }

}
