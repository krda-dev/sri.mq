package com.dajevv.sri.mq.listener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class Mechanics implements MessageListener{

    private final String MECH_QUEUE = "cars.mechanics.queue";

    @Override
    @JmsListener(destination = MECH_QUEUE, containerFactory = "jmsQueueListenerContainerFactory")
    public void onMessage(Message message)  {
        if (message instanceof TextMessage) {
            try {
                String text = ((TextMessage) message).getText();
                System.out.println("Mechanics received: " + text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
