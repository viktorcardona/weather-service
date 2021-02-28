package simple.app.api.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "id",
        "message",
        "country",
        "sunrise",
        "sunset"
})
public class Sys {

    @JsonProperty("type")
    private Integer type;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("message")
    private Double message;
    @JsonProperty("country")
    private String country;
    @JsonProperty("sunrise")
    private Integer sunrise;
    @JsonProperty("sunset")
    private Integer sunset;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
