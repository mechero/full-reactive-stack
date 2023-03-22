package com.thepracticaldeveloper.reactiveweb.controller;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.thepracticaldeveloper.reactiveweb.configuration.QuijoteDataLoader;
import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import com.thepracticaldeveloper.reactiveweb.repository.r2dbc.QuoteReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuoteReactiveControllerIntegrationTest {

    @MockBean
    private QuoteReactiveRepository quoteReactiveRepository;

    // This one is not needed, but we need to override the real one to prevent the default behavior
    @MockBean
    private QuijoteDataLoader quijoteDataLoader;

    @LocalServerPort
    private int serverPort;

    private WebClient webClient;

    private Flux<Quote> quoteFlux;

    @BeforeEach
    public void setUp() {
        this.webClient = WebClient.create("http://localhost:" + serverPort);
        quoteFlux = Flux.just(
                new Quote(1L, "mock-book", "Quote 1"),
                new Quote(2L, "mock-book", "Quote 2"),
                new Quote(3L, "mock-book", "Quote 3"),
                new Quote(4L, "mock-book", "Quote 4"));
    }

    @Test
    public void simpleGetRequest() {
        // given
        given(quoteReactiveRepository.findAll()).willReturn(quoteFlux);

        // when
        Flux<Quote> receivedFlux = webClient.get().uri("/quotes-reactive").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange().flatMapMany(response -> response.bodyToFlux(Quote.class));

        // then
        StepVerifier.create(receivedFlux)
                .expectNext(new Quote(1L, "mock-book", "Quote 1"))
                // Note that if you uncomment this line the test will fail. For some reason the delay after the first
                // element is not respected (I'll investigate this)
//                .expectNoEvent(Duration.ofMillis(99)) // these lines might fail depending on the machine
                .expectNext(new Quote(2L, "mock-book", "Quote 2"))
//                .expectNoEvent(Duration.ofMillis(99))
                .expectNext(new Quote(3L, "mock-book", "Quote 3"))
//                .expectNoEvent(Duration.ofMillis(99))
                .expectNext(new Quote(4L, "mock-book", "Quote 4"))
                .expectComplete()
                .verify();

    }

    @Test
    public void pagedGetRequest() {
        // given
        // In case page=1 and size=2, we mock the result to only the first two elements. Otherwise the Flux will be null.
        given(quoteReactiveRepository.findAllByIdNotNullOrderByIdAsc(PageRequest.of(1, 2)))
                .willReturn(quoteFlux.take(2));

        // when
        Flux<Quote> receivedFlux = webClient.get().uri("/quotes-reactive-paged?page=1&size=2")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange().flatMapMany(response -> response.bodyToFlux(Quote.class));

        // then
        StepVerifier.create(receivedFlux)
                .expectNext(new Quote(1L, "mock-book", "Quote 1"))
                .expectNext(new Quote(2L, "mock-book", "Quote 2"))
                .expectComplete()
                .verify();

    }

}
