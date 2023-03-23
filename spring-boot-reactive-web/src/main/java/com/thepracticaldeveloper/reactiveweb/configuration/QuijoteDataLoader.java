package com.thepracticaldeveloper.reactiveweb.configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Supplier;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import com.thepracticaldeveloper.reactiveweb.repository.r2dbc.QuoteReactiveRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

@Component
public class QuijoteDataLoader implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(QuijoteDataLoader.class);

    private final QuoteReactiveRepository quoteReactiveRepository;

    QuijoteDataLoader(final QuoteReactiveRepository quoteReactiveRepository) {
        this.quoteReactiveRepository = quoteReactiveRepository;
    }

    @Override
    public void run(final ApplicationArguments args) {
        
        // if (quoteReactiveRepository.count().block() == 0L) {
        //     var idSupplier = getIdSequenceSupplier();
        //     var bufferedReader = new BufferedReader(
        //             new InputStreamReader(getClass()
        //                     .getClassLoader()
        //                     .getResourceAsStream("pg2000.txt"))
        //     );
        //     Flux.fromStream(
        //             bufferedReader.lines()
        //                     .filter(l -> !l.trim().isEmpty())
        //                     .map(l -> quoteReactiveRepository.save(
        //                             new Quote(idSupplier.get(),
        //                                     "El Quijote", l))
        //                     )
        //     ).subscribe(m -> log.info("New quote loaded: {}", m.block()));
        //     log.info("Repository contains now {} entries.",
        //             quoteReactiveRepository.count().block());
        // }
    }

    private Supplier<String> getIdSequenceSupplier() {
        return new Supplier<>() {
            Long l = 0L;

            @Override
            public String get() {
                // adds padding zeroes
                return String.format("%05d", l++);
            }
        };
    }
}
