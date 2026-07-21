package com.db.client.auth;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import com.db.model.User;

public class AuthClient {

    private static final String AUTH_ENDPOINT = "/auth";

    @Step("Envia requisição de login para POST /auth")
    public Response login(User user){
        return given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(user)
            .log().ifValidationFails()
        .when()
            .post(AUTH_ENDPOINT)
        .then()
            .log().ifValidationFails()
            .extract()
            .response();
    }

    @Step("Envia requisição de login para POST /auth")
    public Response login(String rawBody){
        return given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(rawBody)
            .log().ifValidationFails()
        .when()
            .post(AUTH_ENDPOINT)
        .then()
            .log().ifValidationFails()
            .extract()
            .response();
    }
}
