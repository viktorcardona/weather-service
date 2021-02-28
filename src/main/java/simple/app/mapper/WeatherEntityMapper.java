package simple.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import simple.app.api.model.WeatherResponse;
import simple.app.entity.WeatherEntity;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(uses = {Collectors.class, Stream.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WeatherEntityMapper {

    WeatherEntityMapper INSTANCE = Mappers.getMapper(WeatherEntityMapper.class);

    @Mapping(expression = "java(null)", target = "id")
    @Mapping(source = "name", target = "city")
    @Mapping(source = "sys.country", target = "country")
    @Mapping(source = "main.temp", target = "temperature")
    WeatherEntity map(WeatherResponse response);

}
