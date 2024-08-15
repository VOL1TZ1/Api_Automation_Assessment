package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class ListOutput {
    Response response;

    @When("I send the booking GET request")
    public void iSendTheBookingGETRequest() {
        response= RestAssured.get(" https://restful-booker.herokuapp.com/booking");

    }

    @Then("The response should be a list that is greater than zero in size")
    public void theResponseShouldBeAListThatIsGreaterThanZero() {
        List<Map<String, Integer>> bookings = response.jsonPath().getList("");

        Assert.assertFalse(bookings.isEmpty());

    }
}
