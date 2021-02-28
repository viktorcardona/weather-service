package simple.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WeatherData {

    private Integer id;
    private String city;
    private String country;
    private Double temperature;
}
