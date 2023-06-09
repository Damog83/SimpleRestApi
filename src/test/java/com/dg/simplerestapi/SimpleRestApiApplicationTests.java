package com.dg.simplerestapi;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimpleRestApiApplicationTests {

    @LocalServerPort
    int port;
    WebClient webClient;

    @BeforeEach
    public void setUp() {
        String baseUrl = "http://localhost:" + port;
        webClient = WebClient.create(baseUrl);
    }

    @Test
    @DirtiesContext
    @DisplayName("check the greeting id increments correctly by one each time a new request is made")
    public void checkGreetingIdIncrementsByOneForEachRequestMade() {
        AtomicLong counter = new AtomicLong(1);

        for(int i = 0; i < 10; i++) {
            Greeting response = webClient.get().uri("/greeting")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Greeting.class)
                    .block();

            long currentCounter = counter.getAndIncrement();
            Assertions.assertNotNull(response);
            Assertions.assertEquals(currentCounter, response.id());
        }
    }

    @ParameterizedTest
    @DirtiesContext
    @DisplayName("check Greeting builder build Greeting method returns a good morning greeting when local time is between 00:00 and 11:59 inclusive")
    @MethodSource("getLocalTimesMorning")
    public void checkGreetingBuilderMorningWithoutRequestParams(LocalTime localTime) {
        Assertions.assertEquals("Good Morning World!", GreetingBuilder.buildGreeting(new String[]{"World"}, localTime));
    }

    private static Stream<LocalTime> getLocalTimesMorning() {
        return Stream.of(
                LocalTime.of(0,0),
                LocalTime.of(3,0),
                LocalTime.of(8,30),
                LocalTime.of(11,59)
        );
    }

    @ParameterizedTest
    @DirtiesContext
    @DisplayName("check Greeting builder build Greeting method returns a good afternoon greeting when local time is between 12:00 and 17:59 inclusive")
    @MethodSource("getLocalTimesAfternoon")
    public void checkGreetingBuilderAfternoonWithoutRequestParams(LocalTime localTime) {
        Assertions.assertEquals("Good Afternoon World!", GreetingBuilder.buildGreeting(new String[]{"World"}, localTime));
    }

    private static Stream<LocalTime> getLocalTimesAfternoon() {
        return Stream.of(
                LocalTime.of(12,0),
                LocalTime.of(14,9),
                LocalTime.of(15,30),
                LocalTime.of(17,59)
        );
    }

    @ParameterizedTest
    @DirtiesContext
    @DisplayName("check Greeting builder build Greeting method returns a good evening greeting when local time is between 18:00 and 20:59 inclusive")
    @MethodSource("getLocalTimesEvening")
    public void checkGreetingBuilderEveningWithoutRequestParams(LocalTime localTime) {
        Assertions.assertEquals("Good Evening World!", GreetingBuilder.buildGreeting(new String[]{"World"}, localTime));
    }

    private static Stream<LocalTime> getLocalTimesEvening() {
        return Stream.of(
                LocalTime.of(18,0),
                LocalTime.of(18,13),
                LocalTime.of(19,30),
                LocalTime.of(20,59)
        );
    }

    @ParameterizedTest
    @DirtiesContext
    @DisplayName("check Greeting builder build Greeting method returns a good night greeting when local time is between 21:00 and 23:59 inclusive")
    @MethodSource("getLocalTimesNight")
    public void checkGreetingBuilderNightWithoutRequestParams(LocalTime localTime) {
        Assertions.assertEquals("Good Night World!", GreetingBuilder.buildGreeting(new String[]{"World"}, localTime));
    }

    private static Stream<LocalTime> getLocalTimesNight() {
        return Stream.of(
                LocalTime.of(21,0),
                LocalTime.of(22,13),
                LocalTime.of(23,30),
                LocalTime.of(23,59)
        );
    }

    @Test
    @DirtiesContext
    @DisplayName("When no name is placed as a request param JSON object returns Hello World! within response body")
    public void checkDefaultMessage() {

        String greeting = GreetingBuilder.buildGreeting(new String[]{"World"}, LocalTime.now());

       Greeting response = webClient.get().uri("/greeting")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Greeting.class)
                .block();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(greeting, response.content());
    }

    @Test
    @DirtiesContext
    @DisplayName("When a single name request param is given correct response is generated ")
    public void checkSingleRequestParam() {

        String greeting = GreetingBuilder.buildGreeting(new String[]{"Bob"}, LocalTime.now());

        Greeting response = webClient.get().uri("/greeting?name=Bob")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Greeting.class)
                .block();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(greeting, response.content());
    }

    @Test
    @DirtiesContext
    @DisplayName("When two name request parameters are given correct response is generated ")
    public void checkTwoRequestParameters() {

        String greeting = GreetingBuilder.buildGreeting(new String[]{"Bob", "Ellie"}, LocalTime.now());

        Greeting response = webClient.get().uri("/greeting?name=Bob&name=Ellie")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Greeting.class)
                .block();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(greeting, response.content());
    }

    @Test
    @DirtiesContext
    @DisplayName("When a three name request parameters are given correct response is generated ")
    public void checkThreeRequestParameters() {

        String greeting = GreetingBuilder.buildGreeting(new String[]{"Bob", "Ellie", "Leo"}, LocalTime.now());

        Greeting response = webClient.get().uri("/greeting?name=Bob&name=Ellie&name=Leo")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Greeting.class)
                .block();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(greeting, response.content());
    }

    @Test
    @DirtiesContext
    @DisplayName("When ten name request parameters are given correct response is generated ")
    public void checkTenRequestParameters() {

        String greeting = GreetingBuilder.buildGreeting(new String[]{"Bob", "Ellie", "Leo", "Sam", "John", "Sarah", "Charlie", "Grant", "Fin", "Liz"}, LocalTime.now());

        Greeting response = webClient.get().uri("/greeting?name=Bob&name=Ellie&name=Leo&name=Sam&name=John&name=Sarah&name=Charlie&name=Grant&name=Fin&name=Liz")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Greeting.class)
                .block();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(greeting, response.content());
    }
}
