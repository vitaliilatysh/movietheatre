package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("ua.epam.spring.hometask.*")
@PropertySource({"file:src/main/resources/auditorium.properties", "file:src/main/resources/db.properties"})
public class AppConfig {

    @Value("${h2.driverClassName}")
    private String driver;

    @Value("${h2.url}")
    private String url;

    @Value("${h2.username}")
    private String username;

    @Value("${h2.password}")
    private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new FileSystemResource("src/main/resources/sql/init.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(getDataSource());
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
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
