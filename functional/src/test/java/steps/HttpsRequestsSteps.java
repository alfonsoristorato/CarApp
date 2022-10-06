package steps;

import com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpsRequestsSteps {
    private static Response response;
    private static String jsonString;
    @When("A request is made to {string} endpoint")
    public void requestTo(String endpoint) {
        RequestSpecification request = RestAssured.given();
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

//    @When("A request is made to {string} endpoint with a body of {int} car")
//
//    public void requestWithBody(String endpoint, Integer numberCars) {
//        List<Object> list = new ArrayList();
//        for (int i = 0; i < numberCars; i++) {
//            String s = Integer.toString(i);
//            Map<String, Object> bodyArray = new HashMap<>();
//
//            bodyArray.put("brand", s);
//            bodyArray.put("model", s);
//            bodyArray.put("year", 2022);
//            bodyArray.put("price", 80000);
//            bodyArray.put("mileage", 10000);
//            bodyArray.put("colour", "lunar grey");
//
//            list.add(bodyArray);
//        }
//        Gson gson = new Gson();
//        String bodyJson = gson.toJson(list);
//        RequestSpecification request = RestAssured.given().body(bodyJson);
//        System.out.println(bodyJson);
//        request.header("Content-Type", "application/json");
//
//        response = request.post(endpoint);
//    }

}
