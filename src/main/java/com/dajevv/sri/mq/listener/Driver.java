package com.dajevv.sri.mq.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class Driver implements MessageListener {


    private final String DRIVER_QUEUE = "cars.driver.queue";

    @Override
    @JmsListener(destination = DRIVER_QUEUE, containerFactory = "jmsQueueListenerContainerFactory")
    public void onMessage(Message message)  {
        if (message instanceof TextMessage) {
            try {
                String text = ((TextMessage) message).getText();
                System.out.println("Driver received: " + text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
