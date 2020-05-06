package com.amdocs.media.assignment.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource(value = {"classpath:database.properties"})
public class DatabaseConfig {
	
	@Autowired 
	private Environment env;

	  @Value("${init-db:false}")
	  private String initDatabase;

	  /**
	   * Place holder configurer property sources placeholder configurer.
	   *
	   * @return the property sources placeholder configurer
	   */
	@Bean
	  public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	  }

	  /**
	   * Jdbc template jdbc template.
	   *
	   * @param dataSource the data source
	   * @return the jdbc template
	   */
	@Bean
	  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	   
		return new JdbcTemplate(dataSource);
	  }

	  /**
	   * Transaction manager platform transaction manager.
	   *
	   * @param dataSource the data source
	   * @return the platform transaction manager
	   */
	@Bean
	  public PlatformTransactionManager transactionManager(DataSource dataSource) {
	    return new DataSourceTransactionManager(dataSource);
	  }

	  /**
	   * Data source data source.
	   *
	   * @return the data source
	   */
	@Bean
	  public DataSource dataSource() {
	    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
	   
	    dataSourceBuilder.driverClassName(env.getProperty("DB_DRIVER_CLASS"));
	    dataSourceBuilder.url(env.getProperty("DB_URL"));
	    dataSourceBuilder.username(env.getProperty("DB_USERNAME"));
	    dataSourceBuilder.password(env.getProperty("DB_PASSWORD"));
	    return dataSourceBuilder.build();
	}

}
