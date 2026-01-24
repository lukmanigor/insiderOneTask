package data;

import static utils.RestApiUtils.API_BASE_URL;

public final class ApiCalls {

    public static final String PET_URL = "v2/pet/";

    public static String getStorePet(int petId) {
        return API_BASE_URL + PET_URL + petId;
    }

    public static String postStorePet() {
        return API_BASE_URL + PET_URL;
    }

    public static String putStorePet() {
        return API_BASE_URL + PET_URL;
    }


}
