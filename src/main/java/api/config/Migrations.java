package api.config;
//
import org.flywaydb.core.Flyway;
//
///**
// * Created by Vileven on 27.03.17.
// */
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Profile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Migrations {
    public static void main(String[] args) throws Exception
    {
        Flyway flyway = new Flyway();
        flyway.setDataSource(System.getenv("JDBC_DATABASE_URL"),
                System.getenv("JDBC_DATABASE_USERNAME"),
                System.getenv("JDBC_DATABASE_PASSWORD"));
        flyway.migrate();
    }

    public static void migrate() throws IOException {
        Flyway flyway = new Flyway();
        System.out.println(System.getenv("JDBC_DATABASE_URL"));
        flyway.setDataSource(System.getenv("JDBC_DATABASE_URL"),
                System.getenv("JDBC_DATABASE_USERNAME"),
                System.getenv("JDBC_DATABASE_PASSWORD"));
        flyway.migrate();
    }
}
