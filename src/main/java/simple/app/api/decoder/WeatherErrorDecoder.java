package simple.app.api.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import simple.app.error.KeyError;
import simple.app.exception.WeatherException;

public class WeatherErrorDecoder implements ErrorDecoder {

    @Override
    public WeatherException decode(String method, Response response) {
        switch (response.status()) {
            case 401:
                return new WeatherException(KeyError.API_WEATHER_KEY);
            case 404:
                return new WeatherException(KeyError.API_WEATHER_DATA_NOT_FOUND);
            default:
                return new WeatherException(KeyError.API_WEATHER_UNHANDLED);
        }
    }

}
