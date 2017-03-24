package api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;


/**
 * Created by Vileven on 24.03.17.
 */
@Configuration
@Profile("heroku")
@PropertySource("application-heroku.properties")
public class HerokuConfiguration {
}
