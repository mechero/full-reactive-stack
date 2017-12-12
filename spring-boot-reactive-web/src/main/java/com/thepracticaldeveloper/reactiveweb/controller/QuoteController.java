package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import com.thepracticaldeveloper.reactiveweb.repository.QuoteMongoBlockingRepository;
import com.thepracticaldeveloper.reactiveweb.repository.QuoteMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class QuoteController {

    private static final int DELAY_PER_ITEM_MS = 100;

    private static final Logger log = LoggerFactory.getLogger(QuoteController.class);

    private QuoteMongoReactiveRepository quoteMongoReactiveRepository;
    private QuoteMongoBlockingRepository quoteMongoBlockingRepository;

    public QuoteController(final QuoteMongoReactiveRepository quoteMongoReactiveRepository,
                           final QuoteMongoBlockingRepository quoteMongoBlockingRepository) {
        this.quoteMongoReactiveRepository = quoteMongoReactiveRepository;
        this.quoteMongoBlockingRepository = quoteMongoBlockingRepository;
    }

    @GetMapping("/quotes-reactive")
    public Flux<Quote> getQuoteFlux() {
        // If you want to use a shorter version of the Flux, use take(100) at the end of the statement below
        return quoteMongoReactiveRepository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
    }

    @GetMapping("/quotes-reactive-paged")
    public Flux<Quote> getQuoteFlux(final @RequestParam(name = "page") int page,
                                    final @RequestParam(name = "size") int size) {
        return quoteMongoReactiveRepository.retrieveAllQuotesPaged(PageRequest.of(page, size))
                .delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
    }

    @GetMapping("/quotes-blocking")
    public Iterable<Quote> getQuotesBlocking() throws Exception {
        Thread.sleep(DELAY_PER_ITEM_MS * quoteMongoBlockingRepository.count());
        return quoteMongoBlockingRepository.findAll();
    }

    @GetMapping("/quotes-blocking-paged")
    public Iterable<Quote> getQuotesBlocking(final @RequestParam(name = "page") int page,
                                             final @RequestParam(name = "size") int size) throws Exception{
        Thread.sleep(DELAY_PER_ITEM_MS * size);
        return quoteMongoBlockingRepository.retrieveAllQuotesPaged(PageRequest.of(page, size));
    }
}
