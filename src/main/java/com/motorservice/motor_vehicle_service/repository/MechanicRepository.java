package com.motorservice.motor_vehicle_service.repository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.motorservice.motor_vehicle_service.entity.Mechanic;

public interface MechanicRepository extends JpaRepository<Mechanic, UUID> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, UUID id);

}
