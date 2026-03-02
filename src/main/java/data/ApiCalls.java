package data;

public final class ApiCalls {
    private ApiCalls() {}

    public static final String PET = "/v2/pet";

    public static String petById(long petId) {
        return PET + "/" + petId;
    }
}