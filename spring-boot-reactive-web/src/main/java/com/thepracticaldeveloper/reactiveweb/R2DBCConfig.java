package com.thepracticaldeveloper.reactiveweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;

@Configuration
@EnableR2dbcRepositories
public class R2DBCConfig extends AbstractR2dbcConfiguration {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Value("${spring.r2dbc.url}")
	private String r2dbcUrl;

	@Override
	public ConnectionFactory connectionFactory() {
		log.info("create R2DBC connection factory");

		// this bean is MANDATORY to be able to inject R2dbcEntityTemplate  
		ConnectionFactory connectionFactory = ConnectionFactories.get(r2dbcUrl);
		return connectionFactory;
	}

	@Bean
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

		// we hook on our ConnectionFactory to execute schema.sql (and possibly sql migrations), which could be very useful in case JPA is not in the project
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

		return initializer;
	}

}
