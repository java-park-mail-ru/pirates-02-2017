//package api.config;
//
//import org.apache.commons.dbcp.BasicDataSource;
//import org.apache.tomcat.jdbc.pool.DataSource;
//import org.flywaydb.core.Flyway;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
///**
// * Created by Vileven on 26.03.17.
// */
//@Profile("heroku")
//@ComponentScan
//@Configuration
//@EnableAutoConfiguration
//public class HerokuConfiguration {
//
//
////    @Bean
////    public BasicDataSource dataSource() throws URISyntaxException {
////        String dbUrl = System.getenv("JDBC_DATABASE_URL");
////        String username = System.getenv("JDBC_DATABASE_USERNAME");
////        String password = System.getenv("JDBC_DATABASE_PASSWORD");
////
////        BasicDataSource basicDataSource = new BasicDataSource();
////        basicDataSource.setUrl(dbUrl);
////        basicDataSource.setUsername(username);
////        basicDataSource.setPassword(password);
////
////        return basicDataSource;
////    }
////
////    public void migrate() {
////        Flyway flyway = new Flyway();
////        flyway.setDataSource(System.getenv("JDBC_DATABASE_URL"),
////                System.getenv("JDBC_DATABASE_USERNAME"),
////                System.getenv("JDBC_DATABASE_PASSWORD"));
////        flyway.migrate();
////    }
//
////    private static Connection getConnection() throws URISyntaxException, SQLException {
////        URI dbUri = new URI(System.getenv("DATABASE_URL"));
////
////        String username = dbUri.getUserInfo().split(":")[0];
////        String password = dbUri.getUserInfo().split(":")[1];
////        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
////
////        return DriverManager.getConnection(dbUrl, username, password);
////    }
//}