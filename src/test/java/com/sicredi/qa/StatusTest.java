
package com.sicredi.qa;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class StatusTest extends AuthTest {

    @Test
    public void verificarStatus() {
        given()
                .spec(spec)
        .when()
                .get("/status")
        .then()
                .statusCode(200);
    }
}
