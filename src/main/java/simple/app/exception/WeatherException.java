package simple.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WeatherException extends RuntimeException {

    private final HttpStatus statusCode;
    private final String code;

    public WeatherException(HttpStatus statusCode, String code, String message) {
        super(message);
        this.statusCode = statusCode;
        this.code = code;
    }
}
