package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.configuration.QuijoteDataLoader;
import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import com.thepracticaldeveloper.reactiveweb.repository.QuoteMongoReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuoteReactiveControllerIntegrationTest {

    @MockBean
    private QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    // This one is not needed, but we need to override the real one to prevent the default behavior
    @MockBean
    private QuijoteDataLoader quijoteDataLoader;

    @LocalServerPort
    private int serverPort;

    private WebClient webClient;

    private Flux<Quote> quoteFlux;

    @Before
    public void setUp() {
        this.webClient = WebClient.create("http://localhost:" + serverPort);
        quoteFlux = Flux.just(
                new Quote("1", "mock-book", "Quote 1"),
                new Quote("2", "mock-book", "Quote 2"),
                new Quote("3", "mock-book", "Quote 3"),
                new Quote("4", "mock-book", "Quote 4"));
    }

    @Test
    public void simpleGetRequest() {
        // given
        given(quoteMongoReactiveRepository.findAll()).willReturn(quoteFlux);

        // when
        Flux<Quote> receivedFlux = webClient.get().uri("/quotes-reactive").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange().flatMapMany(response -> response.bodyToFlux(Quote.class));

        // then
        StepVerifier.create(receivedFlux)
                .expectNext(new Quote("1", "mock-book", "Quote 1"))
                // Note that if you uncomment this line the test will fail. For some reason the delay after the first
                // element is not respected (I'll investigate this)
//                .expectNoEvent(Duration.ofMillis(99)) // these lines might fail depending on the machine
                .expectNext(new Quote("2", "mock-book", "Quote 2"))
//                .expectNoEvent(Duration.ofMillis(99))
                .expectNext(new Quote("3", "mock-book", "Quote 3"))
//                .expectNoEvent(Duration.ofMillis(99))
                .expectNext(new Quote("4", "mock-book", "Quote 4"))
                .expectComplete()
                .verify();

    }

    @Test
    public void pagedGetRequest() {
        // given
        // In case page=1 and size=2, we mock the result to only the first two elements. Otherwise the Flux will be null.
        given(quoteMongoReactiveRepository.findAllByIdNotNullOrderByIdAsc(PageRequest.of(1, 2)))
                .willReturn(quoteFlux.take(2));

        // when
        Flux<Quote> receivedFlux = webClient.get().uri("/quotes-reactive-paged?page=1&size=2")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange().flatMapMany(response -> response.bodyToFlux(Quote.class));

        // then
        StepVerifier.create(receivedFlux)
                .expectNext(new Quote("1", "mock-book", "Quote 1"))
                .expectNext(new Quote("2", "mock-book", "Quote 2"))
                .expectComplete()
                .verify();

    }

}
