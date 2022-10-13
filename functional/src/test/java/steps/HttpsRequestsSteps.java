package steps;

import com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class HttpsRequestsSteps {
    private static Response response;
    private static String jsonString;
    @When("A get request is made to {string} endpoint")
    public void requestTo(String endpoint) {
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        response = request.get(endpoint);
    }

    @Then("A body of {string} is received")
    public void bodyReceived(String body) {

        jsonString = response.asString();
        Assert.assertEquals(body,jsonString);
    }

    @And("A status code of {int} is received")
    public void statusCodeReceived(int code) {
        Assert.assertEquals(code, response.getStatusCode());
    }

    @When("A post request is made to {string} endpoint with a body of {int} car")

    public void requestWithBody(String endpoint, Integer numberCars) {
        List<Object> list = new ArrayList();
        for (int i = 0; i < numberCars; i++) {
            String s = Integer.toString(i);
            Map<String, Object> bodyArray = new HashMap<>();
            bodyArray.put("brand", "test_brand_"+s);
            bodyArray.put("model", "test_model_"+s);
            bodyArray.put("year", 2022);
            bodyArray.put("price", 80000);
            bodyArray.put("mileage", 10000);
            bodyArray.put("colour", "lunar grey");
            list.add(bodyArray);
        }
        Gson gson = new Gson();
        String bodyJson = gson.toJson(list);
        RequestSpecification request = given().body(bodyJson);
        request.header("Content-Type", "application/json");
        response = request.post(endpoint);
    }

    @When("A post request is made to {string} endpoint with a car being {string}")
    public void requestToWithBody(String endpoint, String body) {
        List<Object> bodyList = new ArrayList<>();
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("brand", body.split(", ")[0]);
        bodyMap.put("model", body.split(", ")[1]);
        bodyMap.put("year", Integer.parseInt(body.split(", ")[2]) );
        bodyMap.put("price", Integer.parseInt(body.split(", ")[3]));
        bodyMap.put("mileage", Integer.parseInt(body.split(", ")[4]));
        bodyMap.put("colour", body.split(", ")[5]);
        bodyList.add(bodyMap);
        Gson gson = new Gson();
        String bodyJson = gson.toJson(bodyList);
        RequestSpecification request = given().body(bodyJson);
        request.header("Content-Type", "application/json");
        response = request.post(endpoint);
    }

    @When("An put request is made to {string} endpoint with a car {string}")
    public void  putRequest(String id, String body) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("brand", body.split(", ")[0]);
        bodyMap.put("model", body.split(", ")[1]);
        bodyMap.put("year", Integer.parseInt(body.split(", ")[2]) );
        bodyMap.put("price", Integer.parseInt(body.split(", ")[3]));
        bodyMap.put("mileage", Integer.parseInt(body.split(", ")[4]));
        bodyMap.put("colour", body.split(", ")[5]);
    }

}
