package com.motorservice.motor_vehicle_service.repository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.motorservice.motor_vehicle_service.entity.VehicleOwner;

public interface VehicleOwnerRepository
        extends JpaRepository<VehicleOwner, UUID> {
            boolean existsByPhoneNumber(String phoneNumber);
}
