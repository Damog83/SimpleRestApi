package com.dg.simplerestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class SimpleRestApiApplication {
    public static final Logger logger = Logger.getLogger(SimpleRestApiApplication.class.getName());

    static {
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(FileHandlerConfig.getFileHandler());
    }

    public static void main(String[] args) {
        logger.log(Level.INFO, "SimpleRestApplication app started");
        SpringApplication.run(SimpleRestApiApplication.class, args);
    }

}
