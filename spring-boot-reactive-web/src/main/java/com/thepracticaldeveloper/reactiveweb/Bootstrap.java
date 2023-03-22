package com.thepracticaldeveloper.reactiveweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactoryOptions;
import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Component
public class Bootstrap {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private R2dbcEntityTemplate dbTemplate;

	@EventListener
	public void onStarted(ApplicationStartedEvent event) {
		log.info("init DB - request insert");

		dbTemplate.delete(Quote.class)
			.all()
			.and(
				dbTemplate.insert(Quote.class)
				.using(new Quote("Java pour les nuls", "lorem ipsum"))
			)
			.and(
				dbTemplate.insert(Quote.class)
				.using(new Quote("LOrd of the ring", "lorem ipsum"))
			)
			.and(
				dbTemplate.insert(Quote.class)
				.using(new Quote("Martine fait du Java", "lorem ipsum"))
			)
			.and(
				dbTemplate.select(Quote.class)
					.first()
					.doOnNext(it -> log.info("selected =>" + it))
			)
			.doAfterTerminate(() -> log.info("init TERMINATED"))
			.doOnError(throwable -> log.error("init FAILED", throwable))
			.subscribe();

		log.info("init DB - requests - END");
	}
}
