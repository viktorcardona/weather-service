package simple.app.exception.handler;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import simple.app.error.KeyError;
import simple.app.exception.WeatherException;
import simple.app.mapper.WeatherErrorMapper;
import simple.app.message.GenericMessageReader;
import simple.app.model.WeatherError;

import static simple.app.error.KeyError.*;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class WeatherErrorHandler {

    private final GenericMessageReader messageReader;

    @ExceptionHandler(value = {WeatherException.class})
    public ResponseEntity<WeatherError> handleWeatherException(WeatherException ex) {
        log.error("Weather Exception Occurred...", ex);
        return new ResponseEntity<>(weatherError(ex.getKeyError()), ex.getKeyError().getStatusCode());
    }

    @ExceptionHandler(value = {FeignException.class})
    public ResponseEntity<WeatherError> handleFeignException(FeignException ex) {
        log.error("Feign Exception Occurred...", ex);
        return new ResponseEntity<>(weatherError(API_WEATHER_FEIGN_CLIENT, ex.getMessage()), API_WEATHER_FEIGN_CLIENT.getStatusCode());
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<WeatherError> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("Missing Servlet Request Parameter Exception Occurred...", ex);
        return new ResponseEntity<>(weatherError(CLIENT_BAD_REQUEST, ex.getMessage()), CLIENT_BAD_REQUEST.getStatusCode());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<WeatherError> handleException(Exception ex) {
        log.error("Exception Occurred...", ex);
        return new ResponseEntity<>(weatherError(SERVER_INTERNAL, ex.getMessage()), SERVER_INTERNAL.getStatusCode());
    }

    private WeatherError weatherError(KeyError keyError) {
        return WeatherErrorMapper.INSTANCE.map(keyError.getCode(), messageReader.getMessage(keyError.getKey()));
    }

    private WeatherError weatherError(KeyError keyError, Object... params) {
        return WeatherErrorMapper.INSTANCE.map(keyError.getCode(), messageReader.getMessage(keyError.getKey(), params));
    }

}
