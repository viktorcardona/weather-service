package simple.app.api.decoder;

import feign.Request;
import feign.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import simple.app.error.KeyError;
import simple.app.exception.WeatherException;

import java.util.stream.Stream;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherErrorDecoderTest {

    private WeatherErrorDecoder subject = new WeatherErrorDecoder();

    private static final Request SOME_REQUEST = Request.create(Request.HttpMethod.GET, "some_url", emptyMap(), Request.Body.empty());

    @ParameterizedTest
    @MethodSource("responseAndExpectedExceptionValues")
    void decode_givenErrorResponse_getWeatherException(Response response, KeyError keyError) {
        WeatherException result = subject.decode("some_method", response);
        assertEquals(keyError, result.getKeyError());
    }

    private static Stream<Arguments> responseAndExpectedExceptionValues() {
        return Stream.of(
                Arguments.of(response(401), KeyError.API_WEATHER_KEY),
                Arguments.of(response(404), KeyError.API_WEATHER_DATA_NOT_FOUND),
                Arguments.of(response(500), KeyError.API_WEATHER_UNHANDLED),
                Arguments.of(response(555), KeyError.API_WEATHER_UNHANDLED)
        );
    }

    private static Response response(int status) {
        return Response.builder().request(SOME_REQUEST).status(status).build();
    }

}