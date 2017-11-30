package com.thepracticaldeveloper.reactiveweb.configuration;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import com.thepracticaldeveloper.reactiveweb.repository.QuoteMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.function.LongSupplier;

@Component
public final class QuijoteDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(QuijoteDataLoader.class);

    private QuoteMongoRepository quoteMongoRepository;

    QuijoteDataLoader(final QuoteMongoRepository quoteMongoRepository) {
        this.quoteMongoRepository = quoteMongoRepository;
    }

    @Override
    public void run(final String... args) throws Exception {
        if (quoteMongoRepository.count().block() == 0L) {
            final LongSupplier longSupplier = new LongSupplier() {
                Long l = 0L;

                @Override
                public long getAsLong() {
                    return l++;
                }
            };
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(getClass().getClassLoader().getResourceAsStream("pg2000.txt")));
            Flux.fromStream(
                    bufferedReader.lines().filter(l -> !l.trim().isEmpty())
                            .map(l -> quoteMongoRepository.save(new Quote(String.valueOf(longSupplier.getAsLong()), "El Quijote", l)))
            ).subscribe(m -> log.info("New quote loaded: {}", m.block()));
            log.info("Repository contains now {} entries.", quoteMongoRepository.count().block());
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/pg2000.txt"));
        FileWriter fileWriter = new FileWriter("./src/main/resources/pg2000f.txt");
        StringJoiner stringJoiner = new StringJoiner(" ");
        String line = reader.readLine();
        while (line != null) {
            if (line.trim().isEmpty() && stringJoiner.length() != 0) {
                stringJoiner.add("\n");
                fileWriter.append(stringJoiner.toString());
                stringJoiner = new StringJoiner(" ");
            } else if (!line.trim().isEmpty()){
                stringJoiner.add(line);
            }
            line = reader.readLine();
        }
        fileWriter.close();
        reader.close();
    }
}
