package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class AuthToken {

    private Response response;
    String requestBody;

    @Given("I have a valid username and password")
    public void iHaveAValidUsernameAndPassword() {
        requestBody = """
                {
                "username" : "admin",
                "password" : "password123"
                }""";
    }

    @When("I send the POST request")
    public void iSendAPostRequestToTheAuthEndpoint() {

        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post("https://restful-booker.herokuapp.com/auth");

    }

    @Then("I should receive a token")
    public void iShouldReceiveAValidTokenInTheResponse() {
        String token = response.jsonPath().getString("token");
        Assert.assertFalse(token.isEmpty(), "the token is not empty");
        System.out.println("The Generated Token is: {" + token + "}");
    }
}
