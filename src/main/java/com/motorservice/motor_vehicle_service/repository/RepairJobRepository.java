
package com.motorservice.motor_vehicle_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motorservice.motor_vehicle_service.entity.RepairJob;
import com.motorservice.motor_vehicle_service.enums.RepairJobStatus;

public interface RepairJobRepository extends JpaRepository<RepairJob, UUID> {

    List<RepairJob> findByVehicle_Id(UUID vehicleId);

    List<RepairJob> findByMechanic_Id(UUID mechanicId);
    List<RepairJob> findByStatusOrderByStartDateAsc(RepairJobStatus status);

}
