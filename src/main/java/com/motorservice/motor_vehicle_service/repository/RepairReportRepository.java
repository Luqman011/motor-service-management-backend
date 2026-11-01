package com.motorservice.motor_vehicle_service.repository;
import com.motorservice.motor_vehicle_service.entity.RepairReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepairReportRepository extends JpaRepository<RepairReport, UUID> {
    boolean existsByRepairJob_Id(UUID repairJobId);

}