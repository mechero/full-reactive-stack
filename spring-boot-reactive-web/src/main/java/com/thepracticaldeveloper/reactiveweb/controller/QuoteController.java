package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import com.thepracticaldeveloper.reactiveweb.repository.QuoteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class QuoteController {

    private QuoteRepository quoteRepository;

    public QuoteController(final QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @GetMapping("/quotes")
    public Flux<Quote> getQuoteFlux() {
        return quoteRepository.retrieveAllQuotes().delayElements(Duration.ofSeconds(2));
    }
}
