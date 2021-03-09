package simple.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import simple.app.api.client.WeatherClient;
import simple.app.api.model.WeatherResponse;
import simple.app.entity.WeatherEntity;
import simple.app.mapper.WeatherDataMapper;
import simple.app.mapper.WeatherEntityMapper;
import simple.app.model.WeatherData;
import simple.app.repository.WeatherRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient client;
    private final WeatherRepository repository;
    private final WeatherDataMapper weatherDataMapper;

    public WeatherData getWeather(String city) {
        log.debug("Get weather service was invoked...");
        WeatherResponse response = client.getWeather(city);
        WeatherEntity entity = WeatherEntityMapper.INSTANCE.map(response);
        repository.save(entity);
        return weatherDataMapper.map(entity);
    }

}
