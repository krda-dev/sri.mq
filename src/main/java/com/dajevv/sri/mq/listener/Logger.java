package com.dajevv.sri.mq.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;


@Component
public class Logger {

    @JmsListener(destination = "cars.topic")
    public void consume(String message) throws IOException {
        System.out.println("Logger consumed a message: " + message);
        File file = new File("mq_logs.log");
        if(!file.isFile() && !file.createNewFile()) {
            throw new IOException("Error creating new file: " + file.getAbsolutePath());
        }
        FileWriter writer = new FileWriter(file, true);
        writer.append(message).append("\n");
        writer.flush();
        writer.close();
    }

}
