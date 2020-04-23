package com.dajevv.sri.mq.resource;

import com.dajevv.sri.mq.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
public class DataDistributor {

    Vehicle vehicle;
    JmsTemplate jmsTemplate;
    Topic topic;

    @Autowired
    public DataDistributor(JmsTemplate jmsTemplate, Topic topic) {
        this.vehicle = new Vehicle("Aston Martin");
        this.jmsTemplate = jmsTemplate;
        this.topic = topic;
    }

    @Scheduled(fixedRate = 15000)
    public void publishData() {
        jmsTemplate.convertAndSend(topic, vehicle.toString());
    }
}
