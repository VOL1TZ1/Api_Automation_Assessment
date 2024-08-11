Feature: Booking API Tests

  Scenario: Generate authentication token
    Given I have a valid username and password
    When I send the POST request
    Then I should receive a token

  Scenario: Add a new booking
    Given I have valid booking details
    When I send the POST request
    Then The booking should be added
    And the booking details in the response should match the request
