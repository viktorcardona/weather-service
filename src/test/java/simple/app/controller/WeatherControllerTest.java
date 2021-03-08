package simple.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import simple.app.config.MessageSourceConfig;
import simple.app.exception.handler.WeatherErrorHandler;
import simple.app.message.GenericMessageReader;
import simple.app.service.WeatherService;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WeatherController.class)
@ContextConfiguration(classes = {GenericMessageReader.class, MessageSourceConfig.class})
class WeatherControllerTest {

    @MockBean
    private WeatherService weatherService;

    @Autowired
    private GenericMessageReader messageReader;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setMockMvc() {
        this.mockMvc = standaloneSetup(new WeatherController(weatherService))
                .setControllerAdvice(new WeatherErrorHandler(messageReader))
                .build();
    }

    @Test
    void getWeather_givenValidParameter_status200ByCallingService() throws Exception {
        mockMvc.perform(get("/weather")
                .param("city", "London"))
                .andExpect(status().isOk());
        verify(weatherService).getWeather("London");
    }

}