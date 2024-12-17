package net.javaRabbit.springboot.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaRabbit.springboot.Entity.HourlyEnergyConsumption;
import net.javaRabbit.springboot.Repository.HourlyEnergyConsumptionRepo;
import net.javaRabbit.springboot.WebSocket.WebSocketMessageHandler;
import net.javaRabbit.springboot.dto.EnergyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RabbitMQJsonConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    private final HourlyEnergyConsumptionRepo repository;
    private final WebSocketMessageHandler webSocketMessageHandler;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    private final ConcurrentHashMap<UUID, Float> energyAccumulationMap = new ConcurrentHashMap<>();


    @Autowired
    public RabbitMQJsonConsumer(HourlyEnergyConsumptionRepo repository, ObjectMapper objectMapper, WebSocketMessageHandler webSocketMessageHandler) {
        this.repository = repository;
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
        this.webSocketMessageHandler = webSocketMessageHandler;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(EnergyDTO energyDTO) {
        System.out.println(energyDTO.toString());
        try {
            HourlyEnergyConsumption entity = new HourlyEnergyConsumption();
            entity.setDeviceId(energyDTO.getDevice_id());
            entity.setTimestamp(energyDTO.getTimestamp());
            entity.setHourlyConsumption(energyDTO.getMeasurement_value());
            repository.save(entity);
            logger.info("Energy data saved: " + entity);

            accumulateEnergyAndCheck(energyDTO);

        } catch (Exception e) {
            System.err.println("Error while consuming message: " + e.getMessage());
        }
    }

    private void accumulateEnergyAndCheck(EnergyDTO energyDTO) {
        try {
            UUID deviceId = energyDTO.getDevice_id();
            float currentConsumption = energyDTO.getMeasurement_value();
            energyAccumulationMap.merge(deviceId, currentConsumption, Float::sum);

            String url = "http://reverse-proxy/device-api/device/";
            logger.info(url);
            System.out.println(url);
            String getMaxEnergyUrl = url + "getMaxEnergy/" + deviceId;
            ResponseEntity<Integer> response = restTemplate.getForEntity(getMaxEnergyUrl, Integer.class);
            int maxEnergy = response.getBody();

            float accumulatedEnergy = energyAccumulationMap.get(deviceId);

            if (accumulatedEnergy > maxEnergy) {
                String getUserIdUrl = url + "getUserId/" + deviceId;
                UUID userId = restTemplate.getForEntity(getUserIdUrl, UUID.class).getBody();
                webSocketMessageHandler.sendEnergyExceededNotification(userId.toString(), deviceId.toString(), accumulatedEnergy - maxEnergy);
                String notificationMessage = "Warning: Energy consumption exceeded for device " + deviceId + ". Current consumption: " + accumulatedEnergy + "for user id: " + userId;
                System.out.println("Warning: Energy consumption exceeded for device " + deviceId + ". Current consumption: " + accumulatedEnergy);
                logger.warn(notificationMessage);
            }

        } catch (Exception e) {
            System.err.println("Error while checking or accumulating energy: " + e.getMessage());
        }
    }


}
