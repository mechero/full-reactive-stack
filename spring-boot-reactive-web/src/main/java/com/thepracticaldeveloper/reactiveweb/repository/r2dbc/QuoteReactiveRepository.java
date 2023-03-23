package com.thepracticaldeveloper.reactiveweb.repository.r2dbc;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;

import reactor.core.publisher.Flux;

public interface QuoteReactiveRepository extends R2dbcRepository<Quote, Long> {

    Flux<Quote> findAllByIdNotNullOrderByIdAsc(final Pageable page);
}
