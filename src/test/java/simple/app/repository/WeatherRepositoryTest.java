package simple.app.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import simple.app.entity.WeatherEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;

@DataJpaTest
class WeatherRepositoryTest {

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    void save_givenEntity_isPersisted() {
        WeatherEntity entity = WeatherEntity.builder()
                .city("some_city")
                .country("some_country")
                .temperature(17d)
                .build();

        assertThat(entity.getId(), is(nullValue()));
        weatherRepository.save(entity);
        assertThat(entity.getId(), is(greaterThan(0)));
    }

}