
package com.sicredi.qa;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AddProductTest extends ProductsAuthTest {

    @Test
    public void adicionarProdutoSucesso() {
        given()
                .spec(spec)
                .body("{\"name\":\"Mouse\",\"price\":199.9}")
        .when()
                .post("/products")
        .then()
                .statusCode(201)
                .body("name", Matchers.equalTo("Mouse"))
                .body("price", Matchers.equalTo(199.9f));
    }

    @Test
    public void adicionarProdutoComErro() {
        given()
                .spec(spec)
                .body("{\"name\":\"\"}")
        .when()
                .post("/products")
        .then()
                .statusCode(400)
                .body("error", Matchers.equalTo("name and price are required"));
    }
}
