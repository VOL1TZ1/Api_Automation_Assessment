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

    private Response response;
    private Map<String, Object> bookingDetails;

    @Given("I have valid booking details")
    public void iHaveValidBookingDetails() {
        bookingDetails = new HashMap<>();
        bookingDetails.put("firstname", "Mohamed");
        bookingDetails.put("lastname", "Elmaadawy");
        bookingDetails.put("totalprice", 395);
        bookingDetails.put("depositpaid", true);

        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2024-08-11");
        bookingDates.put("checkout", "2024-08-18");
        bookingDetails.put("bookingdates", bookingDates);

        bookingDetails.put("additionalneeds", "No additional need :)");
    }

    @When("I send the booking POST request")
    public void iSendAPostRequestToTheBookingEndpoint() {
        response = RestAssured.given()
                .contentType("application/json")
                .body(bookingDetails)
                .post("https://restful-booker.herokuapp.com/booking");
    }

    @Then("The booking should be added")
    public void theBookingShouldBeAddedSuccessfully() {
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        int bookingId = response.jsonPath().getInt("bookingid");
        Assert.assertTrue(bookingId > 0, "Booking ID should be greater than 0");
    }

    @Then("the booking details in the response should match the request")
    public void theBookingDetailsInTheResponseShouldMatchTheRequest() {
        Map<String, Object> bookingResponse = response.jsonPath().getMap("booking");

        Assert.assertEquals(bookingResponse.get("firstname"), bookingDetails.get("firstname"));
        Assert.assertEquals(bookingResponse.get("lastname"), bookingDetails.get("lastname"));
        Assert.assertEquals(bookingResponse.get("totalprice"), bookingDetails.get("totalprice"));
        Assert.assertEquals(bookingResponse.get("depositpaid"), bookingDetails.get("depositpaid"));
        Assert.assertEquals(bookingResponse.get("additionalneeds"), bookingDetails.get("additionalneeds"));


    }
}
