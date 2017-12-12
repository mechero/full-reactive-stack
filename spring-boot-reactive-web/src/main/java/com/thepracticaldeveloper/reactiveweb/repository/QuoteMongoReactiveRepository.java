package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface QuoteMongoReactiveRepository extends ReactiveCrudRepository<Quote, String> {

    @Query("{ id: { $exists: true }}")
    Flux<Quote> retrieveAllQuotesPaged(final Pageable page);
}
