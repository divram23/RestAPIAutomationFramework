package TestConfigurations;

import io.restassured.RestAssured;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public static void setup()
    {
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/api";
        RestAssured.port = 8081;

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        RestAssured.requestSpecification = requestSpecification;


        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
              .build();

        RestAssured.responseSpecification = responseSpecification;










    }


}
