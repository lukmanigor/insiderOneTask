package utils;

import data.ApiCalls;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.ApiResponse;
import model.Pet;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class RestApiUtils extends LoggerUtils {

    public static final String API_BASE_URL = "https://petstore.swagger.io";

    static {
        RestAssured.baseURI = API_BASE_URL;
    }

    private static Pet createPet(Pet payload) {
        String endpoint = ApiCalls.PET;
        log.info("API Call: POST " + endpoint);

        try {
            return given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(payload)                 // POJO -> JSON (serialization)
                    .when()
                    .post(endpoint)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(Pet.class);               // JSON -> POJO (deserialization)
        } catch (Exception e) {
            Assert.fail("Exception during createPet API call: " + e.getMessage());
            return null; // unreachable, but keeps compiler happy
        }
    }

    private static Pet getPet(long petId) {
        String endpoint = ApiCalls.petById(petId);
        log.info("API Call: GET " + endpoint);

        try {
            return given()
                    .accept(ContentType.JSON)
                    .when()
                    .get(endpoint)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(Pet.class);
        } catch (Exception e) {
            Assert.fail("Exception during getPet API call: " + e.getMessage());
            return null;
        }
    }

    private static Pet updatePet(Pet payload) {
        String endpoint = ApiCalls.PET;
        log.info("API Call: PUT " + endpoint);

        try {
            return given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put(endpoint)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(Pet.class);
        } catch (Exception e) {
            Assert.fail("Exception during updatePet API call: " + e.getMessage());
            return null;
        }
    }

    private static ApiResponse deletePet(long petId) {
        String endpoint = ApiCalls.petById(petId);
        log.info("API Call: DELETE " + endpoint);

        try {
            return given()
                    .accept(ContentType.JSON)
                    .when()
                    .delete(endpoint)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(ApiResponse.class);
        } catch (Exception e) {
            Assert.fail("Exception during deletePet API call: " + e.getMessage());
            return null;
        }
    }

    // --- Public helpers (like your existing ones) ---

    public static Pet getPetFromStore(long petId) {
        log.trace("getPetFromStore(" + petId + ")");
        Pet pet = getPet(petId);
        log.info("Pet retrieved from store. id=" + pet.getId() + ", name=" + pet.getName());
        return pet;
    }

    public static long addPetToStore(Pet payload) {
        log.info("addPetToStore(petName=" + payload.getName() + ")");
        Pet created = createPet(payload);
        Assert.assertNotNull(created.getId(), "Created pet id is null");
        log.info("Pet added to store. id=" + created.getId());
        return created.getId();
    }

    public static Pet updatePetInStore(Pet payload) {
        log.info("updatePetInStore(petId=" + payload.getId() + ")");
        Pet updated = updatePet(payload);
        log.info("Pet updated in store. id=" + updated.getId() + ", name=" + updated.getName());
        return updated;
    }

    public static ApiResponse removePetFromStore(long petId) {
        log.info("removePetFromStore(" + petId + ")");
        ApiResponse resp = deletePet(petId);
        log.info("Pet removed from store. message=" + resp.getMessage());
        return resp;
    }

    // --- Negative scenarios (status-only validation) ---

    public static void getPetFromStoreInvalidId(long petId) {
        log.trace("getPetFromStoreInvalidId(" + petId + ")");
        String endpoint = ApiCalls.petById(petId);

        Response res = given()
                .accept(ContentType.JSON)
                .when()
                .get(endpoint);

        Assert.assertEquals(
                res.getStatusCode(), 404,
                "Expected 404 for invalid pet id. Actual: " + res.getStatusCode()
        );

        log.info("Get invalid pet response: " + res.getBody().asString());
    }

    public static void addInvalidPetToStore(Object invalidPayloadPojoOrMap) {
        log.info("addInvalidPetToStore(payloadType=" + invalidPayloadPojoOrMap.getClass().getSimpleName() + ")");
        String endpoint = ApiCalls.PET;

        Response res = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(invalidPayloadPojoOrMap)
                .when()
                .post(endpoint);

        // NOTE: Your original code expects 405 but the assertion message said 400 (typo).
        // Keeping 405 to match your existing expectation. :contentReference[oaicite:4]{index=4}
        Assert.assertEquals(
                res.getStatusCode(), 405,
                "Expected 405 for invalid payload. Actual: " + res.getStatusCode()
        );

        log.info("Add invalid pet response: " + res.getBody().asString());
    }

    public static void deletePetFromStoreInvalidId(long petId) {
        log.info("deletePetFromStoreInvalidId(" + petId + ")");
        String endpoint = ApiCalls.petById(petId);

        Response res = given()
                .accept(ContentType.JSON)
                .when()
                .delete(endpoint);

        Assert.assertEquals(
                res.getStatusCode(), 404,
                "Expected 404 for invalid pet id. Actual: " + res.getStatusCode()
        );

        log.info("Delete invalid pet response: " + res.getBody().asString());
    }
}