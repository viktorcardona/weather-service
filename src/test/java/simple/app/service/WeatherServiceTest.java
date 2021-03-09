package simple.app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import simple.app.api.client.WeatherClient;
import simple.app.api.model.Main;
import simple.app.api.model.Sys;
import simple.app.api.model.WeatherResponse;
import simple.app.entity.WeatherEntity;
import simple.app.mapper.WeatherDataMapper;
import simple.app.model.WeatherData;
import simple.app.repository.WeatherRepository;

import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @InjectMocks
    private WeatherService subject;

    @Mock
    private WeatherClient client;

    @Mock
    private WeatherRepository repository;

    @Mock
    private WeatherDataMapper weatherDataMapper;

    @Test
    void getWeather_givenCity_callAPIAndSaveEntity() {

        WeatherEntity entity = WeatherEntity.builder().city("some_city").country("some_country").temperature(2d).build();
        WeatherEntity persistedEntity = WeatherEntity.builder().id(88).city("some_city").country("some_country").temperature(2d).build();
        WeatherData expected = WeatherData.builder().id(88).city("some_city").country("some_country").temperature(2d).build();

        when(client.getWeather("some_city")).thenReturn(getWeatherResponse());
        doAnswer(invocation -> {
            ((WeatherEntity)invocation.getArguments()[0]).setId(88);
            return null;
        }).when(repository).save(entity);
        when(weatherDataMapper.map(persistedEntity)).thenReturn(expected);

        WeatherData result = subject.getWeather("some_city");

        assertThat(result, is(equalToObject(expected)));
        verify(client).getWeather("some_city");
        verify(repository).save(persistedEntity);
        verify(weatherDataMapper).map(persistedEntity);
    }

    private WeatherResponse getWeatherResponse() {
        WeatherResponse response = new WeatherResponse();
        response.setName("some_city");
        Sys sys = new Sys();
        sys.setCountry("some_country");
        response.setSys(sys);
        Main main = new Main();
        main.setTemp(2d);
        response.setMain(main);
        return response;
    }

}