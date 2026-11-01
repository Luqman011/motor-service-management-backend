package com.motorservice.motor_vehicle_service.repository;
import com.motorservice.motor_vehicle_service.entity.RepairReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RepairReportRepository extends JpaRepository<RepairReport, UUID> {
    boolean existsByRepairJob_Id(UUID repairJobId);

    @Query("SELECT r FROM RepairReport r " +
       "JOIN r.repairJob j " +
       "JOIN j.vehicle v " +
       "WHERE v.owner.id = :ownerId")
    List<RepairReport> findRepairReportsByOwner(
        @Param("ownerId") UUID ownerId);

}