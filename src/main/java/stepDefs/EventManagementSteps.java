package stepDefs;

import com.jayway.jsonpath.DocumentContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utilities.PayloadMaker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class EventManagementSteps extends BaseSteps{
Response responseForGetEvents, responseForCreateBooking, responseForCancelBooking;
    @Given("I have events api")
    public void i_have_events_api() {
        // Write code here that turns the phrase above into concrete actions

    }
    @When("I send a request to get events")
    public void i_send_a_request_to_get_events() {
        setEndpointPath(eventsEndpoint);
        setHeadersWithContentType();
        responseForGetEvents = getCall();
    }
    @Then("{string} status and statusCode {int} are returned")
    public void status_and_status_code_are_returned(String status, Integer statusCode) {
        assertThat(responseForGetEvents.statusCode(), equalTo(statusCode));
        assertThat(responseForGetEvents.body().jsonPath().get("status"), equalTo(status));
    }


    @When("I send a request to make a booking with {string}, {string} and {string}")
    public void i_send_a_request_to_make_a_booking_with_and(String eRef, String bType, String sWaitList) {
        setHeadersWithUserId();
        setEndpointPath(bookingsEndpoint);
        PayloadMaker payload = new PayloadMaker();
        DocumentContext reqBody = loadJsonTemplate(creatBookingPayloadPath);
        payload.setPayloadForCreateBooking(reqBody,eRef, bType);

        responseForCreateBooking = getPostCall();
    }

//    @Then("status {string}{string}<bookingType>\" and statusCode {int} are returned")
//    public void statusBookingTypeAndStatusCodeAreReturned(String arg0, String arg1, int arg2) throws Throwable {
//        // Write code here that turns the phrase above into concrete actions    throw new cucumber.api.PendingException();}
//    }
    @Then("status {string}, {string} and statusCode {int} are returned")
    public void statusAndStatusCodeAreReturned(String status, String bType, int statusCode) {
        assertThat(responseForCreateBooking.statusCode(), equalTo(statusCode));
        assertThat(responseForCreateBooking.body().jsonPath().get("status"), equalTo(status));
        assertThat(responseForCreateBooking.body().jsonPath().get("type"), equalTo(bType));
    }

    @When("I cancel the booking with reference {string}")
    public void i_cancel_the_booking_with_reference(String ref) {
        setHeadersWithUserId();
        setEndpointPath(bookingsEndpoint +"/" + ref + "/cancel");
        responseForCancelBooking = getPutCall();
    }

    @Then("the cancellation should be successful with  statusCode {int}")
    public void theCancellationShouldBeSuccessfulWithStatusCode(int statusCode) {
        assertThat(responseForCancelBooking.statusCode(), equalTo(statusCode));
    }


}
