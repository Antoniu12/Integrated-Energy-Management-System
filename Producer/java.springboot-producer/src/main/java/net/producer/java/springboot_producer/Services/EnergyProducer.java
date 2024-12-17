package net.producer.java.springboot_producer.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EnergyProducer {

    private static final String DEVICE_ID_FILE_PATH = "device_id.txt";
    private static final String ENERGY_DATA_FILE_PATH = "sensor.csv";

    public String readDeviceIdFromFile() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DEVICE_ID_FILE_PATH)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + DEVICE_ID_FILE_PATH);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String deviceId = reader.readLine();
                if (deviceId == null || deviceId.trim().isEmpty()) {
                    throw new IOException("Device ID is empty in the file.");
                }
                return deviceId.trim();
            }
        }
    }

    public List<Float> readEnergyDataFromCsv() throws IOException {
        List<Float> energyData = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(ENERGY_DATA_FILE_PATH)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + ENERGY_DATA_FILE_PATH);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    try {
                        energyData.add(Float.parseFloat(values[0].trim()));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid energy data format: " + values[0]);
                    }
                }
            }
        }
        return energyData;
    }

}
