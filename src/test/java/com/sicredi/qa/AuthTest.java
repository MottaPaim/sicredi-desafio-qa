
package com.sicredi.qa;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class AuthTest {

    protected RequestSpecification spec;


    @BeforeClass
    public void setup() {
        spec = new RequestSpecBuilder()
                .setBaseUri("http://localhost:3000")
                .setContentType(ContentType.JSON)
                .build();

        // obtém token JWT do mock‑server
        login();
    }

    protected void login() {
        String token =
                given()
                        .spec(spec)
                        .body("{\"email\":\"user@qa.com\",\"password\":\"123456\"}")
                .when()
                        .post("/auth/login")
                .then()
                        .statusCode(200)
                        .extract()
                        .path("token");

        // adiciona o header Authorization a todos os próximos requests
        spec = new RequestSpecBuilder()
                .setBaseUri("http://localhost:3000")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
