package com.thepracticaldeveloper.reactiveweb.configuration;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import com.thepracticaldeveloper.reactiveweb.repository.QuoteMongoReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.function.Supplier;

@Component
@Slf4j
@RequiredArgsConstructor
public class QuijoteDataLoader implements ApplicationRunner {

    private final QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    @Override
    public void run(final ApplicationArguments args) {
        if (quoteMongoReactiveRepository.count().block() == 0L) {
            var idSupplier = getIdSequenceSupplier();
            var bufferedReader = new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(getClass()
                            .getClassLoader()
                            .getResourceAsStream("pg2000.txt")))
            );
            Flux.fromStream(
                    bufferedReader.lines()
                            .filter(l -> !l.trim().isEmpty())
                            .map(l -> quoteMongoReactiveRepository.save(
                                    new Quote(idSupplier.get(),
                                            "El Quijote", l))
                            )
            ).subscribe(m -> log.info("New quote loaded: {}", m.block()));
            log.info("Repository contains now {} entries.",
                    quoteMongoReactiveRepository.count().block());
        }
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
