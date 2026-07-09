package com.db.client.product;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ProductClient {

    private static final String PRODUCT_ENDPOINT = "/products";

    @Step("Envia requisição para GET /products")
    public Response getAllProducts(){
        return given()
                .accept("application/json")
                .log().ifValidationFails()
            .when()
                .get(PRODUCT_ENDPOINT)
            .then()
                .log().ifValidationFails()
                .extract()
                .response();
    }
}