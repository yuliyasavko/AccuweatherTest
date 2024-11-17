package org.yuliya.accuweather;

import io.qameta.allure.*;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

@Epic("Тестирование Forecast Minutes API")
public class ForecastMinutesTest extends AbstractAccuweatherTest {
    private static final Logger logger = Logger.getLogger(ForecastMinutesTest.class.getName());

    @ParameterizedTest
    @CsvSource({"52.237049,21.017532", "53.9,27.56667"})
    @DisplayName("getForecastResponseByLatLon-Json-Default")
    void getResponseAsStringDefault(double lat, double lon) {
        String json = given().queryParam("apikey", getApiKeyMinute())
                .queryParam("q", lat + "," + lon)
                .when().get(getBaseUrl() + "/forecasts/v1/minute/")
                .then().statusCode(200).time(lessThan(2500L))
                .extract().asString();

        logger.info("Response JSON: " + json);

        Assertions.assertAll(
                () -> Assertions.assertTrue(json.contains("Summary")),
                () -> Assertions.assertTrue(json.contains("Summaries"))
        );
    }

    @ParameterizedTest
    @CsvSource({"52.237049,21.017532", "53.9,27.56667"})
    @DisplayName("getForecastResponseByLatLon-Json-En")
    void getResponseAsStringLanguageEn(double lat, double lon) {
        String json = given().queryParam("apikey", getApiKeyMinute())
                .queryParam("q", lat + "," + lon)
                .queryParam("language", "en-us")
                .when().get(getBaseUrl() + "/forecasts/v1/minute/")
                .then().statusCode(200).time(lessThan(2500L))
                .extract().asString();

        logger.info("Response JSON: " + json);

        Assertions.assertAll(
                () -> Assertions.assertTrue(json.contains("Summary")),
                () -> Assertions.assertTrue(json.contains("Summaries"))
        );
    }

    @ParameterizedTest
    @CsvSource({"52.237049,21.017532", "53.9,27.56667"})
    @DisplayName("getForecastResponseByLatLon-Json-Ru")
    void getResponseAsStringLanguageRu(double lat, double lon) {
        String json = given().queryParam("apikey", getApiKeyMinute())
                .queryParam("q", lat + "," + lon)
                .queryParam("language", "ru")
                .when().get(getBaseUrl() + "/forecasts/v1/minute/")
                .then().statusCode(200).time(lessThan(2500L))
                .extract().asString();

        logger.info("Response JSON: " + json);

        Assertions.assertAll(
                () -> Assertions.assertTrue(json.contains("Summary")),
                () -> Assertions.assertTrue(json.contains("Summaries"))
        );
    }

    @ParameterizedTest
    @CsvSource({"52.237049,21.017532", "53.9,27.56667"})
    @DisplayName("getForecastResponseByLatLon-Json-Invalid")
    void getResponseAsStringLanguageInvalid(double lat, double lon) {
        String json = given().queryParam("apikey", getApiKeyMinute())
                .queryParam("q", lat + "," + lon)
                .queryParam("language", "qwerty")
                .when().get(getBaseUrl() + "/forecasts/v1/minute/")
                .then().statusCode(400).time(lessThan(2500L))
                .extract().asString();

        logger.info("Response JSON: " + json);
    }
}
