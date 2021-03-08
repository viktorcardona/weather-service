package simple.app.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import static java.util.Locale.ENGLISH;

@Component
@RequiredArgsConstructor
public class GenericMessageReader {

    private final MessageSource messageSource;

    public String getMessage(String key, Object... params) {
        return messageSource.getMessage(key, params, ENGLISH);
    }

}
