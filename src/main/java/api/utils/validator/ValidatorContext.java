package api.utils.validator;


import api.services.AccountService;
import api.validators.PasswordValidator;
import api.validators.LoginValidator;
import api.validators.EmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ValidatorContext {

    private final AccountService accountService;

    public ValidatorContext(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Bean
    public PasswordValidator passwordValidator() {
        return new PasswordValidator();
    }

    @Bean
    public LoginValidator loginValidator() {
        return new LoginValidator(accountService);
    }

    @Bean
    public EmailValidator emailValidator() {
        return new EmailValidator(accountService);
    }

}
