package com.dg.simplerestapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") Optional<String[]> names) {
        String[] nameArray = names.orElse(new String[]{"World"});
        return new Greeting(counter.incrementAndGet(), GreetingBuilder.buildGreeting(nameArray, LocalTime.now()));
    }
}
