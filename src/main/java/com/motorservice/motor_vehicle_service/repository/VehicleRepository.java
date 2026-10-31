package com.motorservice.motor_vehicle_service.repository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.motorservice.motor_vehicle_service.entity.Vehicle;


public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    boolean existsByRegistrationNumber(String registrationNumber);

boolean existsByRegistrationNumberAndIdNot(String registrationNumber, UUID id);

List<Vehicle> findByOwner_Id(UUID ownerId);

}
