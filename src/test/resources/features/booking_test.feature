Feature: Booking API Tests

  Scenario: Generate authentication token
    Given I have a valid username and password
    When I send the POST request
    Then I should receive a token

  Scenario: Add a new booking
    Given I have valid booking details
    When I send the booking POST request
    Then The booking should be added
    And the response should match the request

  Scenario: Verify that list of bookings is greater than Zero
    When I send the booking GET request
    Then The response should be a list that is greater than zero in size

