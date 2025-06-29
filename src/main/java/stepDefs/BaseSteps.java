package stepDefs;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.net.MalformedURLException;
import java.net.URL;


public class BaseSteps {
    public Headers headers;
    private String endpointPath;
    private Response response;
    public DocumentContext requestBodyJson;
    String rootEndpoint, bookingEndpoint, creatBookingPayloadPath;
    public BaseSteps() {
        rootEndpoint = "https://restful-booker.herokuapp.com";
        bookingEndpoint = rootEndpoint + "/booking/";
        creatBookingPayloadPath = "/templates/CreateBookingTemplate.json";
    }

    public void setHeaders(Headers value) { restHeaders();
        headers = value; }

    private void restHeaders() {   headers = null;}


    public void setHeadersWithContentType() {
        headers = new Headers(
                new Header("Content-Type", "application/json"));
        setHeaders(headers);  }
    public void setHeadersWithContentTypeAndAccept() {
        headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Accept", "application/json"));
        setHeaders(headers);  }
    public void setHeadersWithContentTypeAndAuth() {
        headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Accept", "application/json"),
                new Header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM="));
        setHeaders(headers);  }

    public void setHeadersAuthorization() {
        headers = new Headers(
                new Header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM="));
        setHeaders(headers);  }

    protected URL getURL() {
        try {
            return new URL(endpointPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException();  }  }
    public Response getCall() {
        response = RestAssured.given().log().all()
                .relaxedHTTPSValidation()
                .headers(headers)
                .when().get(getURL())
                .then().log().all().extract().response();
        return response; }

    public Response getCallWithoutHeader() {
        response = RestAssured.given().log().all()
                .relaxedHTTPSValidation()
                .when().get(getURL())
                .then().log().all().extract().response();
        return response; }


    public Response getPostCall() {
        response = RestAssured.given().log().all()
                .relaxedHTTPSValidation().headers(headers)
                .body(requestBodyJson.jsonString())
                .when().post(getURL())
                .then().log().all().extract().response();
        return response; }

    public Response getPostCallWithoutHeaders() {
        response = RestAssured.given().log().all()
                .relaxedHTTPSValidation()
                .body(requestBodyJson.jsonString())
                .when().post(getURL())
                .then().log().all().extract().response();
        return response; }

    public Response getPutCall() {
        response = RestAssured.given().log().all()          .relaxedHTTPSValidation().headers(headers)
                .body(requestBodyJson.jsonString())
                .when().put(getURL())
                .then().log().all().extract().response();
        return response; }

    public Response deleteCall() {
        response = RestAssured.given().log().all()          .relaxedHTTPSValidation().headers(headers)
                .when().delete(getURL())
                .then().log().all().extract().response();
        return response; }

    public void setEndpointPath(String endpointPath) {
        this.endpointPath = endpointPath;  }
    public DocumentContext loadJsonTemplate(String path) {
        requestBodyJson = JsonPath.parse(this.getClass().getResourceAsStream(path));
        return requestBodyJson;
      }
}


