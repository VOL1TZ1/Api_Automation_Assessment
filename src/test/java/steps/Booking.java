package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class Booking {

    Response response;
    Map<String, Object> requestDetails;

    @Given("I have valid booking details")
    public void iHaveValidBookingDetails() {
        requestDetails = new HashMap<>();
        requestDetails.put("firstname", "Mohamed");
        requestDetails.put("lastname", "Elmaadawy");
        requestDetails.put("totalprice", 395);
        requestDetails.put("depositpaid", true);

        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2024-08-11");
        bookingDates.put("checkout", "2024-08-18");
        requestDetails.put("bookingdates", bookingDates);

        requestDetails.put("additionalneeds", "No additional need :)");
    }

    @When("I send the booking POST request")
    public void iSendTheBookingPOSTRequest() {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestDetails)
                .post("https://restful-booker.herokuapp.com/booking");
    }

    @Then("The booking should be added")
    public void theBookingShouldBeAdded() {
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Then("the response should match the request")
    public void theResponseShouldMatchTheRequest() {
        Map<String, Object> bookingResponse = response.jsonPath().getMap("booking");

        Assert.assertEquals(bookingResponse.get("firstname"), requestDetails.get("firstname"));
        Assert.assertEquals(bookingResponse.get("lastname"), requestDetails.get("lastname"));
        Assert.assertEquals(bookingResponse.get("totalprice"), requestDetails.get("totalprice"));
        Assert.assertEquals(bookingResponse.get("depositpaid"), requestDetails.get("depositpaid"));
        Assert.assertEquals(bookingResponse.get("additionalneeds"), requestDetails.get("additionalneeds"));


    }
}
