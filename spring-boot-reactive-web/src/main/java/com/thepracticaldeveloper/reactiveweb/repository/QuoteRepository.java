package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import reactor.core.publisher.Flux;

public interface QuoteRepository {

    Flux<Quote> retrieveAllQuotes();
}
