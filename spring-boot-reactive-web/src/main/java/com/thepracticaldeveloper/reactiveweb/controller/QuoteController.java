package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import com.thepracticaldeveloper.reactiveweb.repository.QuoteMongoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class QuoteController {

    private QuoteMongoRepository quoteMongoRepository;

    public QuoteController(final QuoteMongoRepository quoteMongoRepository) {
        this.quoteMongoRepository = quoteMongoRepository;
    }

    @GetMapping("/quotes")
    public Flux<Quote> getQuoteFlux() {
        // If you want to use a shorter version of the Flux, use take(100) at the end of the statement below
        return quoteMongoRepository.findAll().delayElements(Duration.ofMillis(10));
    }
}
