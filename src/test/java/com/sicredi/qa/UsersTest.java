package com.sicredi.qa;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UsersTest {

    private RequestSpecification spec;

    @BeforeClass
    public void setup() {
        spec = new RequestSpecBuilder()
                .setBaseUri("http://localhost:3000")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)     // garante Accept: application/json
                .build();
    }

    @Test
    public void buscarUsuarios() {
        given()
                .spec(spec)
                .log().all()            // deixa enquanto depura
                .when()
                .get("/users")
                .then()
                .log().all()
                .statusCode(200);
    }
}
