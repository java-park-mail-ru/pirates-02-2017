package services;

import api.repository.UserRepository;
import api.services.DbUserService;
import api.services.generic.UserService;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Vileven on 24.03.17.
 */

@Configuration
public class ServiceTestConfiguration {

    protected final UserRepository userRepository = Mockito.mock(UserRepository.class);

    @Bean
    public UserService userService() {

        return new DbUserService(userRepository, passwordEncoder());
    }

    @Bean
    public UserRepository userRepository() {
        return userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
