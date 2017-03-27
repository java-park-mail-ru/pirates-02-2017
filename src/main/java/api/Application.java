package api;

import api.config.Migrations;
import api.utils.validator.ValidatorContext;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Import(ValidatorContext.class)
public class Application {

    public static final String SECRET_TEST_KEY = "qcOluDht4uNCJwlIJdTEcxzytdLp4qTp3LLYwxJM";


    public static void main(String[] args) throws Exception {
//        Migrations.migrate();
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }


}

