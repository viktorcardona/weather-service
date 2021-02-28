package simple.app.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import simple.app.api.model.WeatherResponse;

@FeignClient(value = "WeatherClient", url = "${weather.api.baseUrl}")
public interface WeatherClient {

    @GetMapping(value = "${weather.api.path}", produces = "application/json")
    WeatherResponse getWeather(@RequestParam(value = "q") String city);

}
