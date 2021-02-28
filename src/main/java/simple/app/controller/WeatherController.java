package simple.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import simple.app.model.WeatherData;
import simple.app.model.WeatherError;
import simple.app.service.WeatherService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @Operation(summary = "Get weather data by city name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weather data found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherData.class)) }),
            @ApiResponse(responseCode = "400", description = "Missing parameter", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherError.class)) }),
            @ApiResponse(responseCode = "401", description = "Invalid App ID configured", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherError.class)) }),
            @ApiResponse(responseCode = "404", description = "Weather data not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherError.class)) }),
            @ApiResponse(responseCode = "500", description = "Unhandled error getting the weather data", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherError.class)) })
    })
    @GetMapping(value = "/weather", produces = "application/json")
    public WeatherData getWeather(@RequestParam String city) {
        log.debug("Get weather was invoked...");
        return weatherService.getWeather(city);
    }

}
