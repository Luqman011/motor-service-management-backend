package com.motorservice.motor_vehicle_service.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.motorservice.motor_vehicle_service.entity.RepairJob;
import com.motorservice.motor_vehicle_service.enums.RepairJobStatus;

public interface RepairJobRepository extends JpaRepository<RepairJob, UUID> {

    List<RepairJob> findByVehicle_Id(UUID vehicleId);

    List<RepairJob> findByMechanic_Id(UUID mechanicId);
    List<RepairJob> findByStatusOrderByStartDateAsc(RepairJobStatus status);
    List<RepairJob> findByStartDateBetween(LocalDate startDate, LocalDate endDate);


    boolean existsByMechanic_IdAndStartDateAndStatusNot(UUID mechanicId, LocalDate startDate, RepairJobStatus status);

    @Query("SELECT r.status, COUNT(r) FROM RepairJob r GROUP BY r.status")
    List<Object[]> countRepairJobsByStatus();
}
