package com.thepracticaldeveloper.reactiveweb.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thepracticaldeveloper.reactiveweb.repository.jpa.QuoteBlockingRepository;
import com.thepracticaldeveloper.reactiveweb.repository.jpa.QuoteEntity;

@RestController
@CrossOrigin(origins = "*")
public class QuoteBlockingController {

    private static final int DELAY_PER_ITEM_MS = 100;

    private final QuoteBlockingRepository quoteBlockingRepository;

    public QuoteBlockingController(final QuoteBlockingRepository quoteBlockingRepository) {
        this.quoteBlockingRepository = quoteBlockingRepository;
    }

    @GetMapping("/quotes-blocking")
    public Iterable<QuoteEntity> getQuotesBlocking() throws Exception {
        Thread.sleep(DELAY_PER_ITEM_MS * quoteBlockingRepository.count());
        return quoteBlockingRepository.findAll();
    }

    @GetMapping("/quotes-blocking-paged")
    public Iterable<QuoteEntity> getQuotesBlocking(final @RequestParam(name = "page") int page,
                                             final @RequestParam(name = "size") int size) throws Exception {
        Thread.sleep(DELAY_PER_ITEM_MS * size);
        return quoteBlockingRepository.findAllByIdNotNullOrderByIdAsc(PageRequest.of(page, size));
    }
}
