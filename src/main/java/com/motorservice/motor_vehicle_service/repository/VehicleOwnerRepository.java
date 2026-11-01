package com.motorservice.motor_vehicle_service.repository;
import com.motorservice.motor_vehicle_service.entity.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, UUID> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, UUID id);
    @Query("SELECT vo FROM VehicleOwner vo JOIN vo.vehicles v " +
       "WHERE LOWER(v.make) = LOWER(:make)")
    List<VehicleOwner> findOwnersByVehicleMake(@Param("make") String make);
}