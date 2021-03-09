package simple.app.mapper;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import simple.app.entity.WeatherEntity;
import simple.app.model.WeatherData;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ContextConfiguration(classes = WeatherDataMapperTest.SpringTestConfig.class)
@RunWith(Parameterized.class)
public class WeatherDataMapperTest {

    @Autowired
    private WeatherDataMapper subject;

    @ClassRule
    public static final SpringClassRule scr = new SpringClassRule();

    @Rule
    public final SpringMethodRule smr = new SpringMethodRule();

    @Parameter(value = 0)
    public WeatherEntity input;

    @Parameter(value = 1)
    public WeatherData expected;

    @Test
    public void map_givenWeatherResponse_buildWeatherEntity() {
        WeatherData result = subject.map(input);
        assertThat(result, is(equalToObject(expected)));
    }

    @Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> params = new ArrayList();
        params.add(new Object[]{null, null});
        params.add(new Object[]{WeatherEntity.builder().build(), WeatherData.builder().build()});
        params.add(new Object[]{WeatherEntity.builder().id(1).city("some_city").country("some_country").temperature(2.7d).build(), WeatherData.builder().id(1).city("some_city").country("some_country").temperature(Double.valueOf(2.7)).build()});
        return params;
    }

    @Configuration
    @ComponentScan(basePackageClasses = WeatherDataMapperTest.class)
    static class SpringTestConfig {}
}