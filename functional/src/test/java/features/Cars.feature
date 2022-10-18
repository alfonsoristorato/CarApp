Feature: Func Tests for cars/admin endpoint
  Background:
    Given There are no test cars

  Scenario: http post request to endpoint
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand, TestModel, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"New record created in a database\"}" is received
    And A status code of 201 is received

  Scenario: http post request to endpoint with incorrect data
    When A "post" request is made to "cars/admin" endpoint with a car being ", X5, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"Incorrect car data provided\"}" is received
    And A status code of 400 is received

  Scenario: http get request to endpoint gets all cars
    When A get request is made to "cars/admin" endpoint
    And A status code of 200 is received

  Scenario: http get request to endpoint with query gets specific car(s)
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand, TestModel, 2022, 80000, 10000, black"
    Then A get request is made to "cars/admin?brand=TestBrand1&model=TestModel1" endpoint
    And A status code of 200 is received

  Scenario: http get request to endpoint with query with incorrect parameters
    When A get request is made to "cars/admin?brand=Mazda &year=aaaa" endpoint
    And A status code of 400 is received

  Scenario: http put request to endpoint with correct fields updates car item
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand, TestModel, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"New record created in a database\"}" is received
    And A status code of 201 is received
    When A "put" request is made to "cars/admin" endpoint with a car being "TestBrand, TestModel, 2020, 80000, 10000, black"
    Then A body of "{\"description\":\"Cars updated\"}" is received
    And A status code of 200 is received

  Scenario: http put request to endpoint with incorrect fields returns error response
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand, TestModel, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"New record created in a database\"}" is received
    And A status code of 201 is received
    When A "put" request is made to "cars/admin" endpoint with a car being "TestBrand, TestModel, NotAnYear, 80000, 10000, black"
    Then A body of "{\"description\":\"Incorrect car data provided\"}" is received
    And A status code of 400 is received
    When A "put" request is made to "cars/admin" endpoint with a car being "TestBrand, TestModel, 2000, NotAPrice, 10000, black"
    Then A body of "{\"description\":\"Incorrect car data provided\"}" is received
    And A status code of 400 is received

  Scenario: A http delete request to endpoint deletes car item
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand, TestModel, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"New record created in a database\"}" is received
    And A status code of 201 is received
    Then A get request to find the car "TestBrand, TestModel, 2022, 80000, 10000, black" is made and a delete request is made to delete it
    And A status code of 204 is received

  Scenario: A http delete request to endpoint sends back message if no id, or wrong id
    When A delete request is made to "cars/admin" endpoint
    Then A body of "{\"description\":\"Incorrect car data provided\"}" is received
    And A status code of 400 is received
    When A delete request is made to "cars/admin/999999" endpoint
    Then A body of "{\"description\":\"Incorrect car data provided\"}" is received
    And A status code of 400 is received





