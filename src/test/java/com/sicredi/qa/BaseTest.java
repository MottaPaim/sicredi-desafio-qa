package com.sicredi.qa;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

/**
 * Classe base dos testes. Define a baseURI/porta do mock
 * e constrói um RequestSpecification reutilizável.
 */
public abstract class BaseTest {

    protected RequestSpecification spec;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port     = 3000;          // ajuste se mudar a porta do mock

        spec = new RequestSpecBuilder()
                .setContentType("application/json")
                .build();
    }
}
