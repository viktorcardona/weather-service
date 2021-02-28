package simple.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import simple.app.model.WeatherError;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(uses = {Collectors.class, Stream.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WeatherErrorMapper {

    WeatherErrorMapper INSTANCE = Mappers.getMapper(WeatherErrorMapper.class);

    WeatherError map(String code, String message);
}
