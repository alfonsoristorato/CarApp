Feature: Func Tests for cars/admin endpoint
  Scenario: http post request to endpoint
    When A post request is made to "cars/admin" endpoint with a body of 1 car
    Then A body of "{\"description\":\"New record created in a database\"}" is received
    And A status code of 201 is received

  Scenario: http post request to endpoint with incorrect data
    When A post request is made to "cars/admin" endpoint with a car being ", X5, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"Incorrect car data provided\"}" is received
    And A status code of 400 is received

  Scenario: http get request to endpoint gets all cars
    When A get request is made to "cars/admin" endpoint
    And A status code of 200 is received

  Scenario: http get request to endpoint with query gets specific car(s)
    When A post request is made to "cars/admin" endpoint with a car being "Mazda, X5, 2022, 80000, 10000, black"
    Then A get request is made to "cars/admin?brand=Mazda&model=X5" endpoint
    And A status code of 200 is received

  Scenario: http get request to endpoint with query with incorrect parameters
    When A get request is made to "cars/admin?brand=Mazda &year=aaaa" endpoint
    And A status code of 400 is received