package api.utils.validator;

/**
 * Created by coon on 08.03.17.
 */

import api.validators.PasswordValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ValidatorContext {

    @Bean
    public PasswordValidator passwordValidator() {
        return new PasswordValidator();
    }

}
