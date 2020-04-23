package com.dajevv.sri.mq.listener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
class PitStopApprover implements SessionAwareMessageListener<Message> {

    private final String PIT_STOP_QUEUE = "PIT_STOP_QUEUE";

    @Override
    @JmsListener(destination = PIT_STOP_QUEUE, containerFactory = "jmsQueueListenerContainerFactory")
    public void onMessage(Message message, Session session) throws JMSException {
        final ObjectMessage responseMessage = new ActiveMQObjectMessage();
        responseMessage.setJMSCorrelationID(message.getJMSCorrelationID());
        Boolean response = false;
        if((Math.random() < 0.5)) {
            response = true;
        }
        responseMessage.setObject(response);
        final MessageProducer producer = session.createProducer(message.getJMSReplyTo());
        producer.send(responseMessage);
    }
}
