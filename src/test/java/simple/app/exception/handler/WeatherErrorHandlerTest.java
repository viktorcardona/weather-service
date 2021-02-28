package simple.app.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import simple.app.controller.WeatherController;
import simple.app.exception.WeatherException;
import simple.app.mapper.WeatherErrorMapper;
import simple.app.model.WeatherError;
import simple.app.service.WeatherService;

import java.io.UnsupportedEncodingException;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WeatherController.class)
@ContextConfiguration(classes = WeatherErrorMapper.class)
class WeatherErrorHandlerTest {

    @MockBean
    private WeatherService weatherService;

    //@Autowired
    //private WeatherErrorMapper errorMapper;

    @Autowired
    private MockMvc mockMvc;

    private static final Request SOME_REQUEST = Request.create(Request.HttpMethod.GET, "some_url", emptyMap(), Request.Body.empty());

    @BeforeEach
    void setMockMvc() {
        this.mockMvc = standaloneSetup(new WeatherController(weatherService))
                .setControllerAdvice(new WeatherErrorHandler())
                .build();
    }

    @Test
    void errorHandler_handleWeatherException_status401WithCustomErrorCode() throws Exception {
        when(weatherService.getWeather("some_city")).thenThrow(new WeatherException(HttpStatus.UNAUTHORIZED, "some_code", "some_message"));
        WeatherError error = getWeatherError(mockMvc.perform(get("/weather")
                .param("city", "some_city"))
                .andExpect(status().isUnauthorized())
                .andReturn());
        verify(weatherService).getWeather("some_city");
        assertThat(error, is(equalToObject(WeatherError.builder().code("some_code").message("some_message").build())));
    }

    @Test
    void errorHandler_handleFeignException_status500WithCustomErrorCode() throws Exception {
        when(weatherService.getWeather("some_city")).thenThrow(new FeignException.FeignClientException(0, "some_message", SOME_REQUEST, "body".getBytes()));
        WeatherError error = getWeatherError(mockMvc.perform(get("/weather")
                .param("city", "some_city"))
                .andExpect(status().isInternalServerError())
                .andReturn());
        verify(weatherService).getWeather("some_city");
        assertThat(error, is(equalToObject(WeatherError.builder().code("W0006").message("Unknown error while calling the API. Please contact the administrator.").build())));
    }

    @Test
    void errorHandler_handleMissingServletRequestParameterException_status400WithCustomErrorCode() throws Exception {
        WeatherError error = getWeatherError(mockMvc.perform(get("/weather")
                .param("country", "Netherlands"))
                .andExpect(status().isBadRequest())
                .andReturn());
        verify(weatherService, never()).getWeather(anyString());
        assertThat(error.getCode(), is(equalTo("W0004")));
    }

    @Test
    void errorHandler_handleException_status500WithCustomErrorCode() throws Exception {
        when(weatherService.getWeather("some_city")).thenThrow(new RuntimeException("some_unhandled_exception_message"));
        WeatherError error = getWeatherError(mockMvc.perform(get("/weather")
                .param("city", "some_city"))
                .andExpect(status().isInternalServerError())
                .andReturn());
        verify(weatherService).getWeather("some_city");
        assertThat(error, is(equalToObject(WeatherError.builder().code("W0001").message("some_unhandled_exception_message").build())));
    }

    private WeatherError getWeatherError(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), WeatherError.class);
    }
}