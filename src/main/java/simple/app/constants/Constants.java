package simple.app.constants;

public final class Constants {

    public static final String ERROR_CODE_UNHANDLED = "W0001";

    public static final String ERROR_API_AUTH_CODE = "W0002";
    public static final String ERROR_API_AUTH_MESSAGE = "Invalid Api Key";

    public static final String ERROR_API_DEFAULT_CODE = "W0003";
    public static final String ERROR_API_DEFAULT_MESSAGE = "Unexpected error from the API";

    public static final String ERROR_API_BAD_REQUEST_CODE = "W0004";

    public static final String ERROR_API_NOT_FOUND_CODE = "W0005";
    public static final String ERROR_API_NOT_FOUND_MESSAGE = "Data not found for the given city";

    public static final String ERROR_API_CLIENT_CODE = "W0006";
    public static final String ERROR_API_CLIENT_MESSAGE = "Unknown error while calling the API. Please contact the administrator.";

    private Constants() {
        // avoid instantiation
    }
}
