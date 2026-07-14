package base;

import org.testng.annotations.BeforeSuite;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class BaseTest {

    protected static final String BASE_URL = "https://fakestoreapi.com";

    @BeforeSuite(alwaysRun = true)
    public void setupBaseUri(){
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(
            new AllureRestAssured(),
            new RequestLoggingFilter(), 
            new ResponseLoggingFilter());
    }
}