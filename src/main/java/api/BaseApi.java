package api;

import io.restassured.RestAssured;

public class BaseApi {
    final static String BASE_URL = "";
    private final RestAssured restAssured = new RestAssured();
}
