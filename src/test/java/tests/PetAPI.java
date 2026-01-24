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
        // Add a new pet to the store, get the pet, update the pet, and delete the pet
        int iPetId = RestApiUtils.addPetToStore(addPetJSON);
        RestApiUtils.getPetFromStore(iPetId);
        RestApiUtils.updatePetInStore(updatePetJSON);
        RestApiUtils.removePetFromStore(iPetId);

        // Get pet with invalid ID
        RestApiUtils.getPetFromStoreInvalidId(-1);
        // Add invalid pet to store
        RestApiUtils.addInvalidPetToStore(emptyJSON);
        // Update pet with invalid payload
        RestApiUtils.deletePetFromStoreInvalidId(999999);

    }

}
