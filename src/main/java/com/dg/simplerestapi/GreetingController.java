package com.dg.simplerestapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class GreetingController {

    private static final Logger logger = Logger.getLogger(GreetingController.class.getName());

    static {
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(FileHandlerConfig.getFileHandler());
    }

    private final AtomicLong counter = new AtomicLong();


    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") Optional<String[]> names) {
        logger.log(Level.INFO, "@GetMapping for /greeting started");
        logger.log(Level.INFO, "counter value: " + counter);
        String[] nameArray = names.orElse(new String[]{"World"});
        logger.log(Level.INFO, "Name array initialised with values: " + Arrays.toString(nameArray));
        logger.log(Level.INFO, String.format("new Greeting record initialized with values: %s +  %s + %s",counter.get() , Arrays.toString(nameArray), LocalTime.now()));
        return new Greeting(counter.incrementAndGet(), GreetingBuilder.buildGreeting(nameArray, LocalTime.now()));
    }
}
