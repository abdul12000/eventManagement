package utilities;

import com.jayway.jsonpath.DocumentContext;

public class Payload {

    public void setPayloadForCreateBooking(DocumentContext requestBody, String fName, String lName, String tPrice, String dPaid, String cIn, String cOut, String aNeeds){
        requestBody.set("firstname", fName);
        requestBody.set("lastname", lName);
        requestBody.set("totalprice", tPrice);
        requestBody.set("depositpaid", dPaid);
        requestBody.set("bookingdates.checkin", cIn);
        requestBody.set("bookingdates.checkout", cOut);
        requestBody.set("additionalneeds", aNeeds);
    }
}
