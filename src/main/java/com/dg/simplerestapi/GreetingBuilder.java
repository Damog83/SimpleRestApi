package com.dg.simplerestapi;

import java.time.LocalTime;
import java.util.Arrays;

public class GreetingBuilder {

    public static String buildGreeting(String[] names, LocalTime time) {

        int numNames = names.length;
        String greeting;

        if(time.isBefore(LocalTime.NOON)){
            greeting = "Good Morning";
        } else if(time.isBefore(LocalTime.of(18,0))){
            greeting = "Good Afternoon";
        } else if(time.isBefore(LocalTime.of(21,0))) {
            greeting = "Good Evening";
        } else {
            greeting = "Good Night";
        }




            if (numNames == 1) {
            return String.format("%s %s!", greeting, names[0]);
        } else if (numNames == 2) {
            return String.format("%s %s and %s!",greeting,  names[0], names[1]);
        } else {
            String firstNames = String.join(", ", Arrays.copyOfRange(names, 0, numNames - 1));
            return String.format("%s %s and %s!",greeting, firstNames, names[numNames - 1]);
        }
    }
}
