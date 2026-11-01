package com.motorservice.motor_vehicle_service.repository;
import com.motorservice.motor_vehicle_service.entity.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, UUID> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, UUID id);
}