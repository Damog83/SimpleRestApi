package com.dg.simplerestapi;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

@Component
public class FileHandlerConfig {
    public static FileHandler fileHandler = null;
    public static FileHandler getFileHandler() {
        if (fileHandler == null) {
            try {
                fileHandler = new FileHandler("src/main/resources/logFile.log");
                fileHandler.setLevel(Level.ALL);
                fileHandler.setFormatter(new SimpleFormatter());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            return fileHandler;
        }


    @PreDestroy
    public static void closeFileHandler() {
        if(fileHandler != null) {
            getFileHandler().close();
        }
    }
}
