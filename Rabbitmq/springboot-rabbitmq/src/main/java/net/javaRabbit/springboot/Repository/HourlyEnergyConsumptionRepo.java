package net.javaRabbit.springboot.Repository;

import net.javaRabbit.springboot.Entity.HourlyEnergyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HourlyEnergyConsumptionRepo extends JpaRepository<HourlyEnergyConsumption, String> {
}
