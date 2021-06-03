package TestSuites;

import TestConfigurations.BaseTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.*;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PositiveTests extends BaseTest {

    private int id = 3;

    @Test (priority = 0)
    public void verifyStatusCodeGETCustomers()
    {
        Response response = RestAssured.get("/customers");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (priority = 1)
    public void verifyStatusCodeGETSingleCustomer()
    {
        Response response = RestAssured.get("/customers/"+id);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (priority = 2)
    public void verifyStatusCodePOSTCreate()
    {
        Response response = RestAssured.post("/customers");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (priority = 3)
    public void verifyStatusCodePUTUpdateCustomer()
    {
        Response response = RestAssured.put("/customers/"+id);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (priority = 4)
    public void verifyStatusCodeDELETECustomer()
    {
        Response response = RestAssured.delete("/customers/"+id);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (priority = 5)
    public void verifyResponseContentType()
    {
        Response payloadResponse = RestAssured.get("/customers/1");
        String contentType = payloadResponse.header("Content-Type");
        //System.out.println(contentType);

        Assert.assertEquals(contentType, null);

        String responseBody = payloadResponse.getBody().asString();
        Assert.assertEquals(responseBody.contains("firstname"), true);
    }

    @Test (priority = 6)
    public void verifyCreateAndRetrievingCustomerRecord() throws ParseException {
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();

        requestParams.put("firstname", "Adam");
        requestParams.put("lastname", "JohnsonBell");
        requestParams.put("age", "56");
        requestParams.put("id", "8");
        request.body(requestParams.toJSONString());

        Response createResponse = RestAssured.post("/customers");
       // String createResponseBody = createResponse.getBody().asString();

        Response findResponse = RestAssured.get("/customers/8");

        Assert.assertEquals(findResponse.getStatusCode(), 200);

    }

    @Test (priority = 7)
    public void verifyPayloadCustomersSchema()
    {
        given().
                when().
                get("/customers/1").
                then().
                log().ifValidationFails().
                assertThat().
                statusCode(200).
                and().
                contentType("").
                body(matchesJsonSchemaInClasspath("customers_schema.json"));
    }

    @Test (priority = 8)
    public void verifyResponseMatchesFileData() throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONObject data = (JSONObject) parser.parse(new FileReader("src/test/resources/Customer2.json"));

        Response response = get("/customers/2");
        String responseBody = response.asString();

        JSONObject body = (JSONObject) parser.parse(responseBody);

        Assert.assertEquals(data.equals(body), true);
    }

    @Test (priority = 9)
    public void verifyValidatableResponse() throws ParseException {

        ValidatableResponse res = given()
                .when()
                .get("/customers/2")
                .then();

       res.assertThat().statusCode(200);
    }
}
