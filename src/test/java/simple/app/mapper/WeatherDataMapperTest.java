package simple.app.mapper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import simple.app.entity.WeatherEntity;
import simple.app.model.WeatherData;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class WeatherDataMapperTest {

    private WeatherDataMapper subject = WeatherDataMapper.INSTANCE;

    @ParameterizedTest
    @MethodSource("mappingArguments")
    void map_givenWeatherResponse_buildWeatherEntity(WeatherEntity input, WeatherData expected) {
        WeatherData result = subject.map(input);
        assertThat(result, is(equalToObject(expected)));
    }

    private static Stream<Arguments> mappingArguments() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(WeatherEntity.builder().build(), WeatherData.builder().build()),
                Arguments.of(WeatherEntity.builder().id(1).city("some_city").country("some_country").temperature(2.7d).build(), WeatherData.builder().id(1).city("some_city").country("some_country").temperature(Double.valueOf(2.7)).build())
        );
    }

}