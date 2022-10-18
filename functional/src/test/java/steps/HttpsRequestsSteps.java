package steps;

import com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class HttpsRequestsSteps {
    private static Response response;
    private static String jsonString;

    @Given("There are no test cars")
    public void clearTestCars() {
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        response = request.delete("cars/admin/delete-test-data");
    }

    @After
    public void clearTestCarsAfter() {
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        response = request.delete("cars/admin/delete-test-data");
    }

    @When("A delete request is made to {string} endpoint")
    public void deleteRequestTo(String endpoint){
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        response = request.delete(endpoint);

    }

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

    private static Integer TryParseInt(String possibleInt) {
        try {
            return Integer.parseInt(possibleInt);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @When("A {string} request is made to {string} endpoint with a car being {string}")
    public void requestToWithBody(String requestType, String endpoint, String body){
        List<Object> bodyList = new ArrayList<>();
        Map<String, Object> bodyMap = new HashMap<>();

        Integer year = TryParseInt(body.split(", ")[2]);
        Integer price = TryParseInt(body.split(", ")[3]);
        Integer mileage = TryParseInt(body.split(", ")[4]);

        bodyMap.put("brand", body.split(", ")[0]);
        bodyMap.put("model", body.split(", ")[1]);
        bodyMap.put("year",  year == null ? body.split(", ")[2] : year);
        bodyMap.put("price", price == null ? body.split(", ")[3] : price);
        bodyMap.put("mileage", mileage == null ? body.split(", ")[4] : mileage);
        bodyMap.put("colour", body.split(", ")[5]);
        bodyList.add(bodyMap);
        Gson gson = new Gson();
        String bodyJson = gson.toJson(bodyList);
        RequestSpecification request = given().body(bodyJson);
        request.header("Content-Type", "application/json");
        if (requestType.matches("post")){

            response = request.post(endpoint);
        }
        if (requestType.matches("put")){

            response = request.put(endpoint);
        }
    }

    @Then("A get request to find the car {string} is made and a delete request is made to delete it")
    public void requestToGetAndDelete(String body) {
        String endpoint = "cars/admin?brand=" + body.split(", ")[0] + "&model=" + body.split(", ")[1];
        String id = given().when().get(endpoint).then().extract().jsonPath().get("find {it.brand == 'TestBrand'}.id");
        System.out.println(id);
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        response = request.delete("cars/admin/" + id);
    }

}
