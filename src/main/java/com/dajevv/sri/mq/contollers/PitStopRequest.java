package com.dajevv.sri.mq.contollers;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.util.UUID;

@RestController
public class PitStopRequest {

    JmsMessagingTemplate jmsMessagingTemplate;
    JmsTemplate jmsTemplate;
    private final String PIT_STOP_QUEUE;

    @Autowired
    public PitStopRequest(JmsMessagingTemplate jmsMessagingTemplate, JmsTemplate jmsTemplate, @Value("${sbpg.pitstop.queue}") String pitstopQueue) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.jmsTemplate = jmsTemplate;
        this.PIT_STOP_QUEUE = pitstopQueue;
    }

    @GetMapping("/RequestPitstop")
    public void requestPitStop() throws JMSException {
        this.requestPermission();
    }

    public boolean requestPermission() throws JMSException {
        System.out.println("Driver is requesting for PitStop");
        jmsTemplate.setPubSubDomain(false); // queue
        jmsTemplate.setReceiveTimeout(1000L);
        jmsMessagingTemplate.setJmsTemplate(jmsTemplate);
        Session session = jmsMessagingTemplate.getConnectionFactory().createConnection()
                .createSession(false, Session.AUTO_ACKNOWLEDGE);
        ObjectMessage message = session.createObjectMessage(Boolean.class);
        message.setJMSCorrelationID(UUID.randomUUID().toString());
        message.setJMSExpiration(1000L);
        message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(PIT_STOP_QUEUE);
        Boolean response = jmsMessagingTemplate.convertSendAndReceive(activeMQQueue, message, Boolean.class);
        if(response == null) {
            System.out.println("Response from PitStopApprover is null");
            return false;
        } else {
            System.out.println("Response from PitStopApprover " + response);
            return response;
        }
    }
}
