package simple.app.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import simple.app.api.model.WeatherResponse;
import simple.app.entity.WeatherEntity;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class WeatherEntityMapperTest {

    private WeatherEntityMapper subject = WeatherEntityMapper.INSTANCE;

    @ParameterizedTest
    @MethodSource("mappingArguments")
    void map_givenWeatherResponse_buildWeatherEntity(WeatherResponse input, WeatherEntity expected) {
        WeatherEntity result = subject.map(input);
        assertThat(result, is(equalToObject(expected)));
    }

    private static Stream<Arguments> mappingArguments() throws JsonProcessingException {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(responseFull(), WeatherEntity.builder().city("Tokyo").country("JP").temperature(Double.valueOf(285.81)).build()),
                Arguments.of(responseSysCountryNull(), WeatherEntity.builder().city("Tokyo").country(null).temperature(Double.valueOf(285.81)).build()),
                Arguments.of(responseSysNull(), WeatherEntity.builder().city("Tokyo").country(null).temperature(Double.valueOf(285.81)).build()),
                Arguments.of(responseMainTempNull(), WeatherEntity.builder().city("Tokyo").country("JP").temperature(null).build()),
                Arguments.of(responseMainNull(), WeatherEntity.builder().city("Tokyo").country("JP").temperature(null).build())
        );
    }

    private static WeatherResponse responseFull() throws JsonProcessingException {
        return new ObjectMapper().readValue("{\"coord\":{\"lon\":139.6917,\"lat\":35.6895},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"base\":\"stations\",\"main\":{\"temp\":285.81,\"pressure\":1025,\"humidity\":66,\"temp_min\":284.82,\"temp_max\":286.48,\"feels_like\":281.75},\"visibility\":10000,\"wind\":{\"speed\":4.63,\"deg\":180},\"clouds\":{\"all\":40},\"dt\":1613211662,\"sys\":{\"type\":1,\"id\":8074,\"country\":\"JP\",\"sunrise\":1613165445,\"sunset\":1613204421},\"id\":1850147,\"name\":\"Tokyo\",\"cod\":200,\"timezone\":32400}", WeatherResponse.class);
    }

    private static WeatherResponse responseSysCountryNull() throws JsonProcessingException {
        return new ObjectMapper().readValue("{\"coord\":{\"lon\":139.6917,\"lat\":35.6895},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"base\":\"stations\",\"main\":{\"temp\":285.81,\"pressure\":1025,\"humidity\":66,\"temp_min\":284.82,\"temp_max\":286.48,\"feels_like\":281.75},\"visibility\":10000,\"wind\":{\"speed\":4.63,\"deg\":180},\"clouds\":{\"all\":40},\"dt\":1613211662,\"sys\":{\"type\":1,\"id\":8074,\"sunrise\":1613165445,\"sunset\":1613204421},\"id\":1850147,\"name\":\"Tokyo\",\"cod\":200,\"timezone\":32400}", WeatherResponse.class);
    }

    private static WeatherResponse responseSysNull() throws JsonProcessingException {
        return new ObjectMapper().readValue("{\"coord\":{\"lon\":139.6917,\"lat\":35.6895},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"base\":\"stations\",\"main\":{\"temp\":285.81,\"pressure\":1025,\"humidity\":66,\"temp_min\":284.82,\"temp_max\":286.48,\"feels_like\":281.75},\"visibility\":10000,\"wind\":{\"speed\":4.63,\"deg\":180},\"clouds\":{\"all\":40},\"dt\":1613211662,\"id\":1850147,\"name\":\"Tokyo\",\"cod\":200,\"timezone\":32400}", WeatherResponse.class);
    }

    private static WeatherResponse responseMainTempNull() throws JsonProcessingException {
        return new ObjectMapper().readValue("{\"coord\":{\"lon\":139.6917,\"lat\":35.6895},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"base\":\"stations\",\"main\":{\"pressure\":1025,\"humidity\":66,\"temp_min\":284.82,\"temp_max\":286.48,\"feels_like\":281.75},\"visibility\":10000,\"wind\":{\"speed\":4.63,\"deg\":180},\"clouds\":{\"all\":40},\"dt\":1613211662,\"sys\":{\"type\":1,\"id\":8074,\"country\":\"JP\",\"sunrise\":1613165445,\"sunset\":1613204421},\"id\":1850147,\"name\":\"Tokyo\",\"cod\":200,\"timezone\":32400}", WeatherResponse.class);
    }

    private static WeatherResponse responseMainNull() throws JsonProcessingException {
        return new ObjectMapper().readValue("{\"coord\":{\"lon\":139.6917,\"lat\":35.6895},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"base\":\"stations\",\"visibility\":10000,\"wind\":{\"speed\":4.63,\"deg\":180},\"clouds\":{\"all\":40},\"dt\":1613211662,\"sys\":{\"type\":1,\"id\":8074,\"country\":\"JP\",\"sunrise\":1613165445,\"sunset\":1613204421},\"id\":1850147,\"name\":\"Tokyo\",\"cod\":200,\"timezone\":32400}", WeatherResponse.class);
    }

}
