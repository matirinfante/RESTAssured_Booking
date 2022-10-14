package tests;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.runner.Request;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import resources.BookingPayload;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class RESTAssuredTest {

    private RequestSpecification httpRequest;
    private String url;
    private String token;
    private String firstName;
    private String lastName;

    private int roomid;
    private File authJson;

    @BeforeTest
    public void setUp() {
        this.url = "https://automationintesting.online";
        this.httpRequest = given().baseUri(this.url);
        this.authJson = new File("C:\\Users\\Matias\\IdeaProjects\\API_Testing\\src\\test\\java\\tests\\auth.json");
        this.firstName = "Matias";
        this.lastName = "Inf";
    }

    @Test
    public void firstStep() {

        this.httpRequest
                .when()
                .get("/room/")
                .then()
                .statusCode(200)
                .log().body();

        this.httpRequest
                .contentType(ContentType.JSON)
                .body(authJson)
                .when()
                .post("/auth/login")
                .then()
                .log().cookies();
    }

    @Test
    public void secondStep() {
        this.httpRequest
                .contentType(ContentType.JSON)
                .body(authJson)
                .when()
                .post("/auth/login")
                .then()
                .extract().cookie("token");

        this.roomid = this.httpRequest
                .when()
                .get("/room/")
                .then()
                .statusCode(200)
                .extract().response().body().path("rooms[0].roomid");
        String bookingPayload = BookingPayload.bookingDetails(this.firstName, this.lastName, this.roomid).toString();
        this.httpRequest
                .contentType(ContentType.JSON)
                .body(bookingPayload)
                .cookie("token", token)
                .when()
                .post("/booking/")
                .then()
                .log().body()
                .statusCode(201);
    }

}
