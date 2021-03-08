package simple.app.exception;

import lombok.Getter;
import simple.app.error.KeyError;

@Getter
public class WeatherException extends RuntimeException {

    private final KeyError keyError;

    public WeatherException(KeyError keyError) {
        this.keyError = keyError;
    }
}
