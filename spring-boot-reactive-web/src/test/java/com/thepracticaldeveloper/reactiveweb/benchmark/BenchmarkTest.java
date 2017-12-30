package com.thepracticaldeveloper.reactiveweb.benchmark;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BenchmarkTest {

    public static final String BASE_URL = "http://localhost:8080";

    private static final int PARALLELISM = 100;
    private static final int NUMBER_OF_REQUESTS = 1000;

    private final static Logger log = LoggerFactory.getLogger(BenchmarkTest.class);

    @Test
    public void basicTest() throws Exception {
        long start = System.nanoTime();

        RestTemplate restTemplate = new RestTemplate();
        HashMap<Integer, BenchmarkRequestResult> results = new HashMap<>();

        List<Callable<BenchmarkRequestResult>> requestCallableList = IntStream.range(0, NUMBER_OF_REQUESTS)
                .mapToObj(i -> createMonoRequest(i, restTemplate, BASE_URL + "/quotes-blocking-paged?page=1&size=10"))
                .collect(Collectors.toList());

        log.info(" ========== Requests created ");

        ExecutorService executorService = Executors.newFixedThreadPool(PARALLELISM);
        ExecutorCompletionService<BenchmarkRequestResult> completionService = new ExecutorCompletionService<>(executorService);
        requestCallableList.forEach(completionService::submit);

        log.info(" ========== Requests submitted ");

        for (int n = 0; n < requestCallableList.size(); n++) {
            BenchmarkRequestResult benchmarkRequestResult = completionService.take().get();
            results.put(benchmarkRequestResult.getRequestId(), benchmarkRequestResult);
        }

        log.info(" ========== Requests completed ");

        log.info(" ========== RESULTS ========== ");
        double avg = results.values().stream().mapToLong(r -> r.getTookTimeNs()).peek(l -> log.info("" + l)).average().getAsDouble();
        log.info("Average time per request: {}", Duration.ofNanos(Math.round(avg)));

        long end = System.nanoTime();
        log.info(" ========== Benchmark took {} ", Duration.ofNanos(end - start));
    }

    private Callable<BenchmarkRequestResult> createMonoRequest(final int requestId, final RestTemplate restTemplate, final String url) {
        return () -> {
            long start = System.nanoTime();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            long end = System.nanoTime();
            return new BenchmarkRequestResult(requestId, responseEntity, end - start);
        };
    }
}
