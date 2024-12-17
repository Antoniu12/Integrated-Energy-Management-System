package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping(value = "/Devices")
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceService.findDevices();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable("id") UUID deviceId) {
        DeviceDTO dto = deviceService.findDeviceById(deviceId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/getUserDevices/{id}")
    public ResponseEntity<List<DeviceDTO>> getUserDevices(@PathVariable("id") UUID userId){
        List<DeviceDTO> dtos = deviceService.findUserDevices(userId);
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @GetMapping(value = "/getMaxEnergy/{id}")
    public ResponseEntity<Integer> getMaxEnergy(@PathVariable("id") UUID deviceId){
        int energy = deviceService.getEnergy(deviceId);
        return new ResponseEntity<>(energy,HttpStatus.OK);
    }

    @GetMapping(value = "/getUserId/{id}")
    public ResponseEntity<UUID> getUserId(@PathVariable("id") UUID deviceId){
        UUID id = deviceService.findDeviceById(deviceId).getUserId();
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<UUID> insertDevice(@Valid @RequestBody DeviceDetailsDTO deviceDTO) {
        UUID deviceID = deviceService.insert(deviceDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @PostMapping(value = "/deletedUser/{id}")
    public ResponseEntity<String> updateUserDevices(@PathVariable("id") UUID userId) {
        deviceService.updateDevicesUserId(userId);
        return new ResponseEntity<>("Deleted devices for user" + userId, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<UUID> updateDevice(@PathVariable("id") UUID deviceID, @Valid @RequestBody DeviceDetailsDTO deviceDTO){
        UUID device = deviceService.update(deviceID, deviceDTO);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") UUID deviceID){
        deviceService.delete(deviceID);
        return new ResponseEntity<>("Device successfully deleted", HttpStatus.OK);
    }

}
