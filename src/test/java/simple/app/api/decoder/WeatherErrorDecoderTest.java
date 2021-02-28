package simple.app.api.decoder;

import feign.Request;
import feign.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import simple.app.exception.WeatherException;

import java.util.stream.Stream;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherErrorDecoderTest {

    private WeatherErrorDecoder subject = new WeatherErrorDecoder();

    private static final Request SOME_REQUEST = Request.create(Request.HttpMethod.GET, "some_url", emptyMap(), Request.Body.empty());

    @ParameterizedTest
    @MethodSource("responseAndExpectedExceptionValues")
    void decode_givenErrorResponse_getWeatherException(Response response, int status, String code, String message) {
        WeatherException result = subject.decode("some_method", response);
        assertEquals(status, result.getStatusCode().value());
        assertEquals(code, result.getCode());
        assertEquals(message, result.getMessage());
    }

    private static Stream<Arguments> responseAndExpectedExceptionValues() {
        return Stream.of(
                Arguments.of(response(401), 401, "W0002", "Invalid Api Key"),
                Arguments.of(response(404), 404, "W0005", "Data not found for the given city"),
                Arguments.of(response(500), 500, "W0003", "Unexpected error from the API"),
                Arguments.of(response(555), 500, "W0003", "Unexpected error from the API")
        );
    }

    private static Response response(int status) {
        return Response.builder().request(SOME_REQUEST).status(status).build();
    }

}