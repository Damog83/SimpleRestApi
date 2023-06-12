package com.dg.simplerestapi;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GreetingBuilder {

    private static final Logger logger = Logger.getLogger(GreetingBuilder.class.getName());

    static {
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(FileHandlerConfig.getFileHandler());
    }

    public static String buildGreeting(String[] names, LocalTime time) {

        logger.log(Level.INFO,"buildGreeting method started");

        int numNames = names.length;
        logger.log(Level.INFO, "numNames vairiable set to: " + numNames);

        String greeting;

        if (time.isBefore(LocalTime.NOON)) {
            greeting = "Good Morning";
            logger.log(Level.INFO, "Greeting variable set as Good Morning! for Local Time: " + time);
        } else if (time.isBefore(LocalTime.of(18, 0))) {
            greeting = "Good Afternoon";
            logger.log(Level.INFO, "Greeting variable set as Good Afternoon! for Local Time: " + time);
        } else if (time.isBefore(LocalTime.of(21, 0))) {
            greeting = "Good Evening";
            logger.log(Level.INFO,  "Greeting variable set as Good Evening! for Local Time: " + time);
        } else {
            greeting = "Good Night";
            logger.log(Level.INFO, "Greeting variable set as Good Night! for Local Time: " + time);
        }

        if (numNames == 1) {
            String completedGreeting = String.format("%s %s!", greeting, names[0]);
            logger.log(Level.INFO, "Greeting builder returned complete greeting: " + completedGreeting);
            return completedGreeting;
        } else if (numNames == 2) {
            String completedGreeting = String.format("%s %s and %s!", greeting, names[0], names[1]);
            logger.log(Level.INFO, "Greeting builder returned complete greeting: " + completedGreeting);
            return completedGreeting;
        } else {
            String firstNames = String.join(", ", Arrays.copyOfRange(names, 0, numNames - 1));
            String completedGreeting = String.format("%s %s and %s!", greeting, firstNames, names[numNames - 1]);
            logger.log(Level.INFO, "Greeting builder returned complete greeting: " + completedGreeting);
            return completedGreeting;
        }
    }
}
