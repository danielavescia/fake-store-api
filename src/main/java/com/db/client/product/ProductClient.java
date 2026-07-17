package com.db.client.product;

import static io.restassured.RestAssured.given;

import com.db.model.Product;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
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

    @Step("Envia requisição para GET /products com limite de produtos")
    public Response getProductsWithLimit(String limit){
        return given()
                .accept("application/json")
                .queryParam("limit", limit)
                .log().ifValidationFails()
            .when()
                .get(PRODUCT_ENDPOINT)
            .then()
                .log().ifValidationFails()
                .extract()
                .response();
    }

    @Step("Envia requisição para GET /products com ordenação de produtos")
    public Response getProductsSorted(String sort){
        return given()
                .accept("application/json")
                .queryParam("sort", sort)
                .log().ifValidationFails()
            .when()
                .get(PRODUCT_ENDPOINT)
            .then()
                .log().ifValidationFails()
                .extract()
                .response();
    }

    @Step("Envia requisição para GET /products/{id}")
    public Response getProductById(String id){
        return given()
                .accept("application/json")
                .pathParam("id", id)
                .log().ifValidationFails()
            .when()
                .get(PRODUCT_ENDPOINT + "/{id}")
            .then()
                .log().ifValidationFails()
                .extract()
                .response();
    }

    @Step("Envia requisição para POST /products com corpo tipado")
    public Response createProduct(Product product){
        return given()
                .accept("application/json")
                .contentType(ContentType.JSON)
                .body(product)
                .log().ifValidationFails()
            .when()
                .post(PRODUCT_ENDPOINT)
            .then()
                .log().ifValidationFails()
                .extract()
                .response();
    }

    @Step("Envia requisição para POST /products com corpo raw")
    public Response createProductRawBody(String body){
        return given()
                .accept("application/json")
                .contentType(ContentType.JSON)
                .body(body)
                .log().ifValidationFails()
            .when()
                .post(PRODUCT_ENDPOINT)
            .then()
                .log().ifValidationFails()
                .extract()
                .response();
    }

    @Step("Envia requisição para PUT /products")
    public Response updateProduct(Product body, String id){
        return given()
                .accept("application/json")
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(body)
                .log().ifValidationFails()
            .when()
                .put(PRODUCT_ENDPOINT + "/{id}")
            .then()
                .log().ifValidationFails()
                .extract()
                .response();
    }

    @Step("Envia requisição para PUT /products")
    public Response updateProductRawBody(String rawBody, String id){
        return given()
                .accept("application/json")
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(rawBody)
                .log().ifValidationFails()
            .when()
                .put(PRODUCT_ENDPOINT + "/{id}")
            .then()
                .log().ifValidationFails()
                .extract()
                .response();
    }

    @Step("Envia requisição para DELETE /products")
    public Response deleteProduct(String id){
        return given()
                .accept("application/json")
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .log().ifValidationFails()
            .when()
                .delete(PRODUCT_ENDPOINT + "/{id}")
            .then()
                .log().ifValidationFails()
                .extract()
                .response();
    }
}