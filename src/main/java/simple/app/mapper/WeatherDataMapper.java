package simple.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import simple.app.entity.WeatherEntity;
import simple.app.model.WeatherData;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", uses = {Collectors.class, Stream.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WeatherDataMapper {

    WeatherData map(WeatherEntity entity);

}
