package api.utils.validator;


import api.validators.PasswordValidator;
import api.validators.LoginValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ValidatorContext {

    @Bean
    public PasswordValidator passwordValidator() {
        return new PasswordValidator();
    }

    @Bean
    public LoginValidator loginValidator() {
        return new LoginValidator();
    }

}
