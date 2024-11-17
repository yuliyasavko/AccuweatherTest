package org.yuliya.accuweather;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.yuliya.accuweather.weather.DailyForecast;
import org.yuliya.accuweather.weather.Weather;

import java.util.List;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

@Epic("Тестирование Forecast Days API")
public class ForecastDaysTest extends AbstractAccuweatherTest {
    private static final Logger logger = Logger.getLogger(ForecastDaysTest.class.getName());

    @ParameterizedTest
    @DisplayName("getForecastResponse-Weather")
    @CsvSource({"28580,1", "28580,5", "274663,1", "274663,5"})
    void getResponseAsWeather(int location, int days) {
        Weather weather = given().queryParam("apikey", getApiKey()).pathParams("location", location)
                .when().get(getBaseUrl() + "/forecasts/v1/daily/" + days + "day/{location}")
                .then().statusCode(200).time(lessThan(2500L))
                .extract().response().body().as(Weather.class);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(weather.getHeadline()),
                () -> Assertions.assertEquals(days, weather.getDailyForecasts().size()),
                () -> Assertions.assertEquals(18, weather.getDailyForecasts().get(0).getTemperature().getMaximum().getUnitType())
        );
    }

    @ParameterizedTest
    @DisplayName("getForecastResponse-DailyForecast")
    @CsvSource({"28580,1", "28580,5", "274663,1", "274663,5"})
    void getResponseAsDailyForecasts(int location, int days) {
        List<DailyForecast> dailyForecasts = given().queryParam("apikey", getApiKey()).pathParams("location", location)
                .when().get(getBaseUrl() + "/forecasts/v1/daily/" + days + "day/{location}")
                .then().statusCode(200).time(lessThan(2500L))
                .extract().body().jsonPath().getList("DailyForecasts", DailyForecast.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(days, dailyForecasts.size()),
                () -> Assertions.assertEquals(18, dailyForecasts.get(0).getTemperature().getMaximum().getUnitType())
        );
    }

    @ParameterizedTest
    @CsvSource({"28580,1", "28580,5", "274663,1", "274663,5"})
    @DisplayName("getForecastResponse-Json")
    void getResponseAsString(int location, int days) {
        String json = given().queryParam("apikey", getApiKey()).pathParams("location", location)
                .when().get(getBaseUrl() + "/forecasts/v1/daily/" + days + "day/{location}")
                .then().statusCode(200).time(lessThan(2500L))
                .extract().asString();

        logger.info("Response JSON: " + json);

        Assertions.assertAll(
                () -> Assertions.assertTrue(json.contains("Headline")),
                () -> Assertions.assertTrue(json.contains("DailyForecasts"))
        );
    }

}
