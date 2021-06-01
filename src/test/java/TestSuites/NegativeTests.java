package TestSuites;

import TestConfigurations.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeTests extends BaseTest {

    private int id = 1000;

    @Test
    public void validateDeletingNonExistingCustomer()
    {
        Response deleteResponse = RestAssured.delete("/customers/"+id);
        String deleteResponseBody = deleteResponse.getBody().asString();
        Assert.assertEquals(deleteResponseBody.contains("undefined"), true);

    }

    @Test
    public void verifyUpdatingNonExistingCustomer()
    {
        Response updateResponse = RestAssured.put("customers/"+id);
        String putResponseBody = updateResponse.getBody().asString();
        Assert.assertEquals(putResponseBody.contains("Don't Exist Customer:"), true);
    }

    @Test
    public void verifyCreatingExistingCustomer()
    {
        Response createResponse1 = RestAssured.post("/customers");
        String createResponseBody1 = createResponse1.getBody().asString();

        Response createResponse2 = RestAssured.post("/customers");
        String createResponseBody2 = createResponse2.getBody().asString();
        Assert.assertEquals(createResponseBody2.contains("Post Successfully"), true);

    }
}
