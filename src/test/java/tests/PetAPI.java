package tests;

import org.testng.annotations.Test;
import utils.RestApiUtils;

import java.io.File;

public class PetAPI extends BaseTest{

    File addPetJSON = new File("src/test/resources/payloads/create_dog.json");
    File updatePetJSON = new File("src/test/resources/payloads/update_pet.json");
    File emptyJSON = new File("src/test/resources/payloads/empty_file.json");

    @Test
    public void testPetAPI() {
        // Add a new pet to the store
        int iPetId = RestApiUtils.addPetToStore(addPetJSON);
        // Get pet from store
        RestApiUtils.getPetFromStore(iPetId);
        // Update pet in store
        RestApiUtils.updatePetInStore(updatePetJSON);
        // Remove pet from store
        RestApiUtils.removePetFromStore(iPetId);
    }

    @Test
    public void testPetAPIInvalid(){
        // Get pet with invalid ID
        RestApiUtils.getPetFromStoreInvalidId(-1);
        // Add invalid pet to store
        RestApiUtils.addInvalidPetToStore(emptyJSON);
        // Delete pet with invalid id
        RestApiUtils.deletePetFromStoreInvalidId(999999);

    }

}
