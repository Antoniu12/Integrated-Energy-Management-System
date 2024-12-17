package net.producer.java.springboot_producer.Controller;

import net.producer.java.springboot_producer.DTO.EnergyDTO;
import net.producer.java.springboot_producer.Producer.Producer;
import net.producer.java.springboot_producer.Services.EnergyProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/rabbitmq")
public class MessageJsonController {

    private Producer jsonProducer;

    public MessageJsonController(Producer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage() throws IOException {

        EnergyProducer producer = new EnergyProducer();
        UUID deviceId = UUID.fromString(producer.readDeviceIdFromFile());
        List<Float> energyData = producer.readEnergyDataFromCsv();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(new Runnable() {
            int index = 0;
            long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                if (index < energyData.size()) {

                    EnergyDTO energyDTO = new EnergyDTO();
                    energyDTO.setTimestamp((int) ((System.currentTimeMillis() - startTime) / 1000));
                    energyDTO.setDevice_id(deviceId);
                    energyDTO.setMeasurement_value(energyData.get(index));
                    jsonProducer.sendJsonMessage(energyDTO);

                    System.out.println("Sent EnergyDTO: " + energyDTO);

                    index++;
                } else {
                    scheduler.shutdown();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        return ResponseEntity.ok("Json message sent to RabbitMQ");
    }
}
