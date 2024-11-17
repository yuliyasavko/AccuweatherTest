package org.yuliya.accuweather;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.yuliya.accuweather.location.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

@Epic("Тестирование Location API")
public class LocationTest extends AbstractAccuweatherTest {
    private static final Logger logger = Logger.getLogger(LocationTest.class.getName());

    @Test
    @DisplayName("getLocation-Minsk-Json")
    void getLocationMinskAsStringTest() {
        Map<String, String> params = new HashMap<>();
        params.put("apikey", getApiKey());
        params.put("q", "Minsk");
        String json = given().queryParams(params)
                .when().get(getBaseUrl() + "/locations/v1/cities/autocomplete")
                .then().statusCode(200).time(lessThan(2500L))
                .extract().asString();

        logger.info("Response JSON: " + json);

        Assertions.assertAll(
                () -> Assertions.assertTrue(json.contains("Minsk")),
                () -> Assertions.assertTrue(json.contains("Belarus"))
        );
    }

    @Test
    @DisplayName("getLocation-Minsk-Location")
    void getLocationMinskAsLocation() {
        Map<String, String> params = new HashMap<>();
        params.put("apikey", getApiKey());
        params.put("q", "Minsk");
        List<Location> locations = given().queryParams(params)
                .when().get(getBaseUrl() + "/locations/v1/cities/autocomplete")
                .then().statusCode(200).time(lessThan(2500L))
                .extract().body().jsonPath().getList(".", Location.class);
        Assertions.assertAll(
                () -> Assertions.assertEquals("Minsk", locations.get(0).getLocalizedName()),
                () -> Assertions.assertEquals("Belarus", locations.get(0).getCountry().getLocalizedName())
        );
    }

    @Test
    @DisplayName("getLocation-Warsaw-Json")
    void getLocationWarsawAsStringTest() {
        Map<String, String> params = new HashMap<>();
        params.put("apikey", getApiKey());
        params.put("q", "Warsaw");
        String json = given().queryParams(params)
                .when().get(getBaseUrl() + "/locations/v1/cities/autocomplete")
                .then().statusCode(200).time(lessThan(2500L))
                .extract().asString();

        logger.info("Response JSON: " + json);

        Assertions.assertAll(
                () -> Assertions.assertTrue(json.contains("Warsaw")),
                () -> Assertions.assertTrue(json.contains("Poland"))
        );
    }

    @Test
    @DisplayName("getLocation-Warsaw-Location")
    void getLocationWarsawAsLocation() {
        Map<String, String> params = new HashMap<>();
        params.put("apikey", getApiKey());
        params.put("q", "Warsaw");
        List<Location> locations = given().queryParams(params)
                .when().get(getBaseUrl() + "/locations/v1/cities/autocomplete")
                .then().statusCode(200).time(lessThan(2500L))
                .extract().body().jsonPath().getList(".", Location.class);
        Assertions.assertAll(
                () -> Assertions.assertEquals("Warsaw", locations.get(0).getLocalizedName()),
                () -> Assertions.assertEquals("Poland", locations.get(0).getCountry().getLocalizedName())
        );
    }
}