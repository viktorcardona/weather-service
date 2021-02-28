package simple.app.api.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import simple.app.exception.WeatherException;

import static org.springframework.http.HttpStatus.*;
import static simple.app.constants.Constants.*;

public class WeatherErrorDecoder implements ErrorDecoder {

    @Override
    public WeatherException decode(String method, Response response) {
        switch (response.status()) {
            case 401:
                return new WeatherException(UNAUTHORIZED, ERROR_API_AUTH_CODE, ERROR_API_AUTH_MESSAGE);
            case 404:
                return new WeatherException(NOT_FOUND, ERROR_API_NOT_FOUND_CODE, ERROR_API_NOT_FOUND_MESSAGE);
            default:
                return new WeatherException(INTERNAL_SERVER_ERROR, ERROR_API_DEFAULT_CODE, ERROR_API_DEFAULT_MESSAGE);
        }
    }

}
