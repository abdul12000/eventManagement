@smoke
Feature:
  Creation of events and bookings
  Scenario: Get list of events
    Given I have events api
    When I send a request to get events
    Then "SUCCESS" status and statusCode 200 are returned

  @smoke
  Scenario Outline: Book an event
    Given I have events api
    When I send a request to make a booking with "<eventRef>", "<bookingType>" and "<shouldWaitList>"
    Then status "<status>", "<bookingType>" and statusCode 200 are returned
    Examples:
      | eventRef                             | bookingType | shouldWaitList |status|
      | 3fa85f64-5717-4562-b3fc-2c963f66afa6 | REGULAR     | true           |    SUCCESSFUL  |
      | c0a8015a-000f-11ee-be56-0242ac120002 | REGULAR     | true           |      WAIT_LISTED|

  Scenario Outline: Cancel a booking
    Given I have events api
    When I cancel the booking with reference "<bookingRef>"
    Then the cancellation should be successful with  statusCode 200
    Examples:
      | bookingRef                       |
      | 3bd424b23ead4b9894a0261058ada225 |