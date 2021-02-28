package simple.app.mapper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import simple.app.model.WeatherError;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class WeatherErrorMapperTest {

    private WeatherErrorMapper subject = WeatherErrorMapper.INSTANCE;

    @ParameterizedTest
    @MethodSource("mappingArguments")
    void map_givenInput_createWeatherError(String code, String message, WeatherError expected) {
        WeatherError result = subject.map(code, message);
        assertThat(result, is(equalToObject(expected)));
    }

    private static Stream<Arguments> mappingArguments() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of("some_code", "some_message", WeatherError.builder().code("some_code").message("some_message").build()),
                Arguments.of(null, "some_message", WeatherError.builder().code(null).message("some_message").build()),
                Arguments.of("some_code", null, WeatherError.builder().code("some_code").message(null).build())
        );
    }

}