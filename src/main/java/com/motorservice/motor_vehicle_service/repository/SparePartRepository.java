
package com.motorservice.motor_vehicle_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motorservice.motor_vehicle_service.entity.SparePart;

public interface SparePartRepository extends JpaRepository<SparePart, UUID> {

    List<SparePart> findByRepairJob_Id(UUID repairJobId);

}
