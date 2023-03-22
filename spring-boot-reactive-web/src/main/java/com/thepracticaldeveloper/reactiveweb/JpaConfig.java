package com.thepracticaldeveloper.reactiveweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.thepracticaldeveloper.reactiveweb.repository.jpa.QuoteBlockingRepository;
import com.thepracticaldeveloper.reactiveweb.repository.jpa.QuoteEntity;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories
public class JpaConfig  {

	private final Logger log = LoggerFactory.getLogger(getClass());

	

}
