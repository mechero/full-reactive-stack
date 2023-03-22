package com.thepracticaldeveloper.reactiveweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import com.thepracticaldeveloper.reactiveweb.repository.r2dbc.QuoteReactiveRepository;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;

// @Configuration
// @EnableR2dbcRepositories(basePackageClasses = QuoteReactiveRepository.class)
// public class R2DBCConfig extends AbstractR2dbcConfiguration {

// 	private final Logger log = LoggerFactory.getLogger(getClass());


// 	@Value("${spring.r2dbc.url}") String r2dbcUrl;

// 	@Override
// 	public ConnectionFactory connectionFactory() {

// 		log.info("create R2DBC connection factory");
// 		ConnectionFactory connectionFactory = ConnectionFactories.get(r2dbcUrl);
// 		return connectionFactory;
// 	}

// 	// @Bean
// 	// public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
// 	// 	return new R2dbcEntityTemplate(connectionFactory);
// 	// }

// 	@EventListener
// 	public void onStarted(ApplicationStartedEvent event, 
// 	@Autowired R2dbcEntityTemplate dbTemplate) {
// 		log.info("init DB");
// 		dbTemplate.getDatabaseClient().sql("CREATE TABLE quote" +
// 				"(id bigint PRIMARY KEY auto_increment," +
// 				"book VARCHAR(255)," +
// 				"content TEXT)")
// 				.fetch()
// 				.rowsUpdated();

// 		dbTemplate.insert(Quote.class)
// 				.using(new Quote("Java pour les nuls", "lorem ipsum"));

// 		dbTemplate.select(Quote.class)
// 				.first()
// 				.doOnNext(it -> log.info("selected =>" + it));
// 	}
// }
