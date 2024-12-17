package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.Device;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByDescription(String description);
    @Query(value = "SELECT d FROM Device d WHERE d.user_id = :user_id")
    List<Device> findUserDevices(@Param("user_id") UUID userId);

    @Query(value = "SELECT d " +
            "FROM Device d " +
            "WHERE d.description = :description "
    )
    Optional<Device> findSeniorsByName(@Param("description") String description);

    @Query(value = "SELECT d.energy_consumption FROM Device d WHERE d.id = :device_id")
    int getMaxEnergy(@Param("device_id") UUID deviceId);

}
