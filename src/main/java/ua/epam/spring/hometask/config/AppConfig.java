package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("ua.epam.spring.hometask.*")
@PropertySource({"file:src/main/resources/auditorium.properties", "file:src/main/resources/db.properties"})
public class AppConfig {

    @Value("${driverClassName}")
    private String driver;

    @Value("${url}")
    private String url;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSourceBuilder = new DriverManagerDataSource();
        dataSourceBuilder.setDriverClassName(driver);
        dataSourceBuilder.setUrl(url);
        dataSourceBuilder.setUsername(username);
        dataSourceBuilder.setPassword(password);
        return dataSourceBuilder;
    }

}
