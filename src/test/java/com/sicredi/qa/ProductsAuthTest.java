
package com.sicredi.qa;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ProductsAuthTest extends AuthTest {

    @Test
    public void listarProdutos() {
        given()
                .spec(spec)
        .when()
                .get("/products")
        .then()
                .statusCode(200);
    }
}
