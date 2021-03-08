package simple.app.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class MessageSourceConfig {

    private static final String MESSAGE_PROPERTIES = "messages/messages";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames(MESSAGE_PROPERTIES);
        resourceBundleMessageSource.setDefaultEncoding(UTF_8.name());
        return resourceBundleMessageSource;
    }

}
