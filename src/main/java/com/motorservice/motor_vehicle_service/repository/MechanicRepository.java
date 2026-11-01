package com.motorservice.motor_vehicle_service.repository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.motorservice.motor_vehicle_service.entity.Mechanic;

public interface MechanicRepository extends JpaRepository<Mechanic, UUID> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, UUID id);

    @Query("SELECT m FROM Mechanic m WHERE m.repairJobs IS EMPTY")
    List<Mechanic> findMechanicsWithoutRepairJobs();

}
