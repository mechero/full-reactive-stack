package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface QuoteMongoRepository extends ReactiveCrudRepository<Quote, String> {
}
