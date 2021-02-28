package simple.app.repository;

import org.springframework.data.repository.CrudRepository;
import simple.app.entity.WeatherEntity;

public interface WeatherRepository extends CrudRepository<WeatherEntity, Integer> {
}