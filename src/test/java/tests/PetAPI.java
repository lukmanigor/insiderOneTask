package tests;

import model.ApiResponse;
import model.Category;
import model.Pet;
import model.Tag;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.RestApiUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PetAPI extends BaseTest {

    private static Pet buildPet(long id, String name, String status) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);

        Category category = new Category();
        category.setId(1L);
        category.setName("dogs");
        pet.setCategory(category);

        pet.setPhotoUrls(Arrays.asList("https://example.com/dog.png"));

        Tag tag = new Tag();
        tag.setId(10L);
        tag.setName("friendly");
        pet.setTags(Arrays.asList(tag));

        return pet;
    }

    @Test
    public void testPetAPI() {
        // Use a unique-ish id to avoid collisions across runs
        long petId = System.currentTimeMillis();

        // 1) Add a new pet to the store (POJO -> JSON)
        Pet createPayload = buildPet(petId, "Rex", "available");
        long createdId = RestApiUtils.addPetToStore(createPayload);
        Assert.assertEquals(createdId, petId, "Created pet id mismatch.");

        // 2) Get pet from store (JSON -> POJO)
        Pet fetched = RestApiUtils.getPetFromStore(createdId);
        Assert.assertEquals(fetched.getId().longValue(), createdId, "Fetched pet id mismatch.");
        Assert.assertEquals(fetched.getName(), "Rex", "Fetched pet name mismatch.");
        Assert.assertEquals(fetched.getStatus(), "available", "Fetched pet status mismatch.");

        // 3) Update pet in store
        // Petstore PUT /pet expects the full object, including id
        Pet updatePayload = buildPet(createdId, "Rex Updated", "pending");
        Pet updated = RestApiUtils.updatePetInStore(updatePayload);
        Assert.assertEquals(updated.getId().longValue(), createdId, "Updated pet id mismatch.");
        Assert.assertEquals(updated.getName(), "Rex Updated", "Updated pet name mismatch.");
        Assert.assertEquals(updated.getStatus(), "pending", "Updated pet status mismatch.");

        // 4) Remove pet from store
        ApiResponse deleteResp = RestApiUtils.removePetFromStore(createdId);
        Assert.assertNotNull(deleteResp, "Delete response is null.");
        // message format varies in Petstore, but it usually includes the id
        Assert.assertTrue(
                deleteResp.getMessage() != null && deleteResp.getMessage().contains(String.valueOf(createdId)),
                "Delete response message doesn't contain pet id. message=" + deleteResp.getMessage()
        );
    }

    @Test
    public void testPetAPIInvalid() {
        // 1) Get pet with invalid ID
        RestApiUtils.getPetFromStoreInvalidId(-1);

        // 2) Add invalid pet to store: send "{}" via a Map (instead of empty_file.json)
        Map<String, Object> emptyJson = new HashMap<>();
        RestApiUtils.addInvalidPetToStore(emptyJson);

        // 3) Delete pet with invalid id
        RestApiUtils.deletePetFromStoreInvalidId(999999);
    }
}