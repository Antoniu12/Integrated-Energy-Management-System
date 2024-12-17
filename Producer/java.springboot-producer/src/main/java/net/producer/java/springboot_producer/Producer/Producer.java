package net.producer.java.springboot_producer.Producer;

import net.producer.java.springboot_producer.DTO.EnergyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private RabbitTemplate rabbitTemplate;

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(EnergyDTO energyDTO){
        LOGGER.info(String.format("Json message sent -> %s", energyDTO.toString()));
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, energyDTO);
    }
}
