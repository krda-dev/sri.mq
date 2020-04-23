package com.dajevv.sri.mq.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CamelRouter extends RouteBuilder {

    @Value("${sbpg.topic.in}")
    private String topicIn;

    @Value("${sbpg.driver.queue}")
    private String driverQueue;

    @Value("${sbpg.mechanics.queue}")
    private String mechanicsQueue;

//    String topicIn = "jms:topic:cars.topic";

//    String driverQueue = "jms:queue:cars.driver.queue";

//    String mechanicsQueue = "jms:queue:cars.mechanics.queue";

//    String queueOut_3 = "jms:queue:OUT_3";

    private static final int MAX_ENGINE_TEMPERATURE = 120;
    private static final int MAX_TIRE_PRESSURE = 300;
    private static final int MIN_OIL_PRESSURE = 150;
    private static final double SAFETY_SWITCH = 0.85;

    @Override
    public void configure() throws Exception {
        from(topicIn)
                .process(exchange -> {
                    String convertedMessage = (String) exchange.getIn().getBody();
                    String[] vehicleData = convertedMessage.split(",");
                    System.out.println("MonitoringApp consumed a message: " + convertedMessage);
                    if(infoForDriver(vehicleData)) {
                        System.out.println("Warning the driver");
                        convertedMessage += ", warn_Driver";
                    }
                    if(infoForMechanics(vehicleData)) {
                        System.out.println("Warning mechanics team");
                        convertedMessage += ", warn_Mechanics";
                    }
                    System.out.println(convertedMessage);
                    exchange.getIn().setBody(convertedMessage);
                })
                .choice()
                    .when()
                        .simple("${body} contains 'warn_Driver'")
                        .to(driverQueue)
                .choice()
                    .when()
                        .simple("${body} contains 'warn_Mechanics'")
                        .to(mechanicsQueue)
//                .otherwise()
//                    .to(queueOut_3)
                .endChoice();
    }

    private boolean infoForDriver(String[] values) {
        System.out.println("Checking infoForDriver");
        float engineTemp = Float.parseFloat(values[1]);
        float tirePress = Float.parseFloat(values[2]);
        float oilPress = Float.parseFloat(values[3]);
        return engineTemp > MAX_ENGINE_TEMPERATURE * SAFETY_SWITCH  || tirePress > MAX_TIRE_PRESSURE * SAFETY_SWITCH  || oilPress < MIN_OIL_PRESSURE * SAFETY_SWITCH;
    }
    private boolean infoForMechanics(String[] values) {
        System.out.println("Checking infoForMechanics");
        float engineTemp = Float.parseFloat(values[1]);
        float tirePress = Float.parseFloat(values[2]);
        float oilPress = Float.parseFloat(values[3]);
        return engineTemp > MAX_ENGINE_TEMPERATURE  || tirePress > MAX_TIRE_PRESSURE  || oilPress < MIN_OIL_PRESSURE;
    }
}
