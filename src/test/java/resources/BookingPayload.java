package resources;

import org.apache.groovy.json.internal.Dates;
import org.json.JSONArray;
import org.json.JSONObject;


public class BookingPayload {
    public static JSONObject bookingDetails(String firstName, String lastName, int roomId) {

        JSONObject body = new JSONObject();
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2022-12-25");
        bookingDates.put("checkout", "2022-12-26");
        body.put("bookingdates", bookingDates);
        body.put("depositpaid", true);
        body.put("firstname", firstName);
        body.put("lastname", lastName);
        body.put("roomid", roomId);
        body.put("totalprice", 0);

        return body;
    }
}
