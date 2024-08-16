package steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Booking {

    Response response;
    Map<String, Object> requestDetails;
    String dataPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "Data", "Data.json").toString();

    @Given("I have valid booking details")
    public void iHaveValidBookingDetails() throws IOException {
        requestDetails = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        requestDetails = objectMapper.readValue(new File(dataPath), new TypeReference<HashMap<String, Object>>() {});
        System.out.println("Request: " + requestDetails);
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

        Map<String, String> bookingDatesResponse = (Map<String, String>) bookingResponse.get("bookingdates");
        Map<String, String> bookingDatesRequest = (Map<String, String>) requestDetails.get("bookingdates");

        Assert.assertEquals(bookingDatesResponse.get("checkin"), bookingDatesRequest.get("checkin"));
        Assert.assertEquals(bookingDatesResponse.get("checkout"), bookingDatesRequest.get("checkout"));


    }
}
