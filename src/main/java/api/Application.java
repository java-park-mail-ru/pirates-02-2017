package api;

import api.services.generic.AbstractAccountService;
import api.utils.test.TestHeaderInterceptor;
import api.utils.validator.ValidatorContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@SpringBootApplication
@Import(ValidatorContext.class)
public class Application {

    public static final String SECRET_TEST_KEY = "qcOluDht4uNCJwlIJdTEcxzytdLp4qTp3LLYwxJM";


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }


}

