package com.thepracticaldeveloper.reactiveweb.benchmark;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This is a test class intended to use as a benchmark of the application, that's why the test is ignored by default.
 *
 * The goal is to demonstrate how a Reactive Web approach performs in a better way under a high request load compared
 * with a classic blocking solution.
 *
 * To use it, first run the application. The test assumes it'll be running on localhost:8080, but you can change the
 * constant if you want.
 *
 * It's recommended that you discard the fist execution results since they could include some warming up of the server.
 */
@Ignore
public class BenchmarkTest {

    private static final int PARALLELISM = 32;
    private static final String BASE_URL = "http://localhost:8080";

    private final static Logger log = LoggerFactory.getLogger(BenchmarkTest.class);

    @Test
    public void benchmarkOneRequestBlocking() throws Exception {
        runBenchmark(1, PARALLELISM, "/quotes-blocking-paged?page=1&size=10");
    }

    @Test
    public void benchmarkOneRequestReactive() throws Exception {
        runBenchmark(1, PARALLELISM, "/quotes-reactive-paged?page=1&size=10");
    }

    @Test
    public void benchmark8RequestBlocking() throws Exception {
        runBenchmark(8, PARALLELISM, "/quotes-blocking-paged?page=1&size=10");
    }

    @Test
    public void benchmark8RequestReactive() throws Exception {
        runBenchmark(8, PARALLELISM, "/quotes-reactive-paged?page=1&size=10");
    }

    @Test
    public void benchmark32RequestBlocking() throws Exception {
        runBenchmark(32, PARALLELISM, "/quotes-blocking-paged?page=1&size=10");
    }

    @Test
    public void benchmark32RequestReactive() throws Exception {
        runBenchmark(32, PARALLELISM, "/quotes-reactive-paged?page=1&size=10");
    }

    @Test
    public void benchmark96RequestBlocking() throws Exception {
        runBenchmark(96, PARALLELISM, "/quotes-blocking-paged?page=1&size=10");
    }

    @Test
    public void benchmark96RequestReactive() throws Exception {
        runBenchmark(96, PARALLELISM, "/quotes-reactive-paged?page=1&size=10");
    }

    @Test
    public void benchmark768RequestBlocking() throws Exception {
        runBenchmark(768, PARALLELISM, "/quotes-blocking-paged?page=1&size=10");
    }

    @Test
    public void benchmark768RequestReactive() throws Exception {
        runBenchmark(768, PARALLELISM, "/quotes-reactive-paged?page=1&size=10");
    }

    private void runBenchmark(final int requests, final int parallelism, final String context) throws Exception {
        long start = System.nanoTime();

        WebClient webClient = WebClient.create(BASE_URL);
        HashMap<Integer, BenchmarkRequestResult> results = new HashMap<>();

        List<Callable<BenchmarkRequestResult>> requestCallableList = IntStream.range(0, requests)
                .mapToObj(i -> createMonoRequest(i, webClient, context))
                .collect(Collectors.toList());

        log.info(" ========== NEW BENCHMARK --> Requests: {}, Parallelism: {}, URL: {}", requests, parallelism, context);
        log.info(" ========== Requests created ");

        ExecutorService executorService = Executors.newFixedThreadPool(parallelism);
        ExecutorCompletionService<BenchmarkRequestResult> completionService = new ExecutorCompletionService<>(executorService);
        requestCallableList.forEach(completionService::submit);

        log.info(" ========== Requests submitted @ {}", Duration.ofNanos(System.nanoTime() - start));

        for (int n = 0; n < requestCallableList.size(); n++) {
            BenchmarkRequestResult benchmarkRequestResult = completionService.take().get();
            results.put(benchmarkRequestResult.getRequestId(), benchmarkRequestResult);
        }

        log.info(" ========== Requests completed @ {}", Duration.ofNanos(System.nanoTime() - start));

        log.info(" ========== RESULTS ========== ");
        double avg = results.values().stream().mapToLong(r -> r.getTookTimeNs()).peek(l -> log.debug("" + l)).average().getAsDouble();
        log.info("Average time per request: {}", Duration.ofNanos(Math.round(avg)));
        double successRate = results.values().stream().
                filter(r -> r.getResponseEntity().getStatusCode().equals(HttpStatus.OK)).count() * 100.0 /
                results.size();
        double errorRate = 100.0 - successRate;
        log.info("Success Rate: {}", successRate);
        log.info("Error Rate:   {}", errorRate);
        int nQuotes = results.values().stream().map(r -> r.getResponseEntity().getBody()).mapToInt(this::countQuotes).sum();
        log.info("Total Number of Quotes: {}", nQuotes);

        long end = System.nanoTime();
        log.info(" ========== Benchmark took {} ", Duration.ofNanos(end - start));

        assertThat(nQuotes).as("Number of total Received Quotes must be equal to the number " +
                "of requests times 10 (results per page)").isEqualTo(requests * 10);
    }

    private Callable<BenchmarkRequestResult> createMonoRequest(final int requestId, final WebClient webClient, final String url) {
        return () -> {
            long start = System.nanoTime();
            ResponseEntity<String> responseEntity = webClient.get().uri(url).exchange().block().toEntity(String.class).block();
            long end = System.nanoTime();
            return new BenchmarkRequestResult(requestId, responseEntity, end - start);
        };
    }

    private int countQuotes(final String s) {
        return StringUtils.countOccurrencesOf(s, "\"id\"");
    }
}
