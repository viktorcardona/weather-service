package simple.app.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum KeyError {

    SERVER_INTERNAL("W0001", INTERNAL_SERVER_ERROR, "error.001.internal"),

    CLIENT_BAD_REQUEST("W0004", BAD_REQUEST, "error.004.http.bad.request"),

    API_WEATHER_KEY("W0002", UNAUTHORIZED, "error.002.api.key"),
    API_WEATHER_UNHANDLED("W0003", INTERNAL_SERVER_ERROR, "error.003.api.unhandled"),
    API_WEATHER_DATA_NOT_FOUND("W0005", NOT_FOUND, "error.005.api.not.found"),
    API_WEATHER_FEIGN_CLIENT("W0006", INTERNAL_SERVER_ERROR, "error.006.api.client");

    final String code;
    final HttpStatus statusCode;
    final String key;

}
