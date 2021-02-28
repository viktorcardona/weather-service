package simple.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import simple.app.entity.WeatherEntity;
import simple.app.model.WeatherData;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(uses = {Collectors.class, Stream.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WeatherDataMapper {

    WeatherDataMapper INSTANCE = Mappers.getMapper(WeatherDataMapper.class);

    WeatherData map(WeatherEntity entity);

}
