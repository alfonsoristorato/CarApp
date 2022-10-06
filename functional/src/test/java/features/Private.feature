Feature: Func Tests for private/status endpoint
  Scenario: http request to endpoint
    When A request is made to "private/status" endpoint
    Then A body of "OK" is received
    And A status code of 200 is received