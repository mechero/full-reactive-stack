package com.thepracticaldeveloper.reactiveweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.thepracticaldeveloper.reactiveweb.repository.jpa")
@Import(DataSourceAutoConfiguration.class)
public class JpaConfig  {

	private final Logger log = LoggerFactory.getLogger(getClass());


}
