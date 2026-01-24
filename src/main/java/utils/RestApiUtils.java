package utils;

import data.ApiCalls;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;

public class RestApiUtils extends LoggerUtils{

    public static final String API_BASE_URL = "https://petstore.swagger.io/";

    private static Response createPet(File payload) {
        String sApiCall = ApiCalls.postStorePet();
        log.info("API Call: " + sApiCall);
        Response response = null;
        try {
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception during API call: " + e.getMessage());
        }
        return response;
    }

    private static Response getPet(int petId) {
        String sApiCall = ApiCalls.getStorePet(petId);
        log.info("API Call: " + sApiCall);
        Response response = null;
        try {
            response = RestAssured.given().when()
                    .get(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception during API call: " + e.getMessage());
        }
        return response;
    }

    private static Response updatePet(File payload) {
        String sApiCall = ApiCalls.putStorePet();
        log.info("API Call: " + sApiCall);
        Response response = null;
        try {
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception during API call: " + e.getMessage());
        }
        return response;
    }

    private static Response deletePet(int petId) {
        String sApiCall = ApiCalls.getStorePet(petId);
        log.info("API Call: " + sApiCall);
        Response response = null;
        try {
            response = RestAssured.given().when()
                    .delete(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception during API call: " + e.getMessage());
        }
        return response;
    }

    public static void getPetFromStore(int petId) {
        log.trace("getPetFromStore(" + petId + "))");
        Response response = getPet(petId);
        int status = response.getStatusCode();
        Assert.assertEquals(status, 200, "API call failed with status code: " + status);
        String sResponseBody = response.getBody().asString();
        log.info("Pet retrieved from store. Response body: " + sResponseBody);
    }

    public static int addPetToStore(File payload) {
        log.info("addPetToStore(" + payload.getName() + "))");
        Response response = createPet(payload);
        int status = response.getStatusCode();
        Assert.assertEquals(status, 200, "API call failed with status code: " + status);
        int petId = response.jsonPath().getInt("id");
        String sResponseBody = response.getBody().asString();
        log.info("Pet added to store. Response body: " + sResponseBody);
        return petId;
    }

    public static void updatePetInStore(File payload) {
        log.info("updatePetInStore(" + payload.getName() + "))");
        Response response = updatePet(payload);
        int status = response.getStatusCode();
        Assert.assertEquals(status, 200, "API call failed with status code: " + status);
        String sResponseBody = response.getBody().asString();
        log.info("Pet updated in store. Response body: " + sResponseBody);
    }

    public static void removePetFromStore(int petId) {
        log.info("removePetFromStore(" + petId + "))");
        Response response = deletePet(petId);
        int status = response.getStatusCode();
        Assert.assertEquals(status, 200, "API call failed with status code: " + status);
        String sResponseBody = response.getBody().asString();
        log.info("Pet removed from store. Response body: " + sResponseBody);
    }

    public static void getPetFromStoreInvalidId(int petId) {
        log.trace("getPetFromStoreInvalidId(" + petId + "))");
        Response response = getPet(petId);
        int status = response.getStatusCode();
        Assert.assertEquals(status, 404, "API call did not return expected 404 status code for invalid ID. Actual status code: " + status);
        String sResponseBody = response.getBody().asString();
        log.info("Get pet with invalid ID response body: " + sResponseBody);
    }

    public static void addInvalidPetToStore(File payload) {
        log.info("addInvalidPetToStore(" + payload.getName() + "))");
        Response response = createPet(payload);
        int status = response.getStatusCode();
        Assert.assertEquals(status, 405, "API call did not return expected 400 status code for invalid payload. Actual status code: " + status);
        String sResponseBody = response.getBody().asString();
        log.info("Add invalid pet to store response body: " + sResponseBody);
    }

    public static void deletePetFromStoreInvalidId(int petId) {
        log.info("deletePetFromStoreInvalidId(" + petId + "))");
        Response response = deletePet(petId);
        int status = response.getStatusCode();
        Assert.assertEquals(status, 404, "API call did not return expected 404 status code for invalid ID. Actual status code: " + status);
        String sResponseBody = response.getBody().asString();
        log.info("Delete pet with invalid ID response body: " + sResponseBody);
    }

}
