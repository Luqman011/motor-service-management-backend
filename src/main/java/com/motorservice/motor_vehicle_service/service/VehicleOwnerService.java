package com.motorservice.motor_vehicle_service.service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.motorservice.motor_vehicle_service.entity.VehicleOwner;
import com.motorservice.motor_vehicle_service.repository.VehicleOwnerRepository;

@Service
public class VehicleOwnerService {

    private final VehicleOwnerRepository vehicleOwnerRepository;

    public VehicleOwnerService(VehicleOwnerRepository vehicleOwnerRepository) {
        this.vehicleOwnerRepository = vehicleOwnerRepository;
    }

    // Register a vehicle owner
    public VehicleOwner saveOwner(VehicleOwner vehicleOwner) {

    if (vehicleOwnerRepository.existsByPhoneNumber(
            vehicleOwner.getPhoneNumber())) {

        throw new RuntimeException("Vehicle owner already exists");
    }

    return vehicleOwnerRepository.save(vehicleOwner);
}

    // Retrieve all vehicle owners
    public List<VehicleOwner> getAllOwners() {
        return vehicleOwnerRepository.findAll();
    }

    // Retrieve one vehicle owner by ID
    public Optional<VehicleOwner> getOwnerById(UUID id) {
        return vehicleOwnerRepository.findById(id);
    }

    // Update a vehicle owner
    public Optional<VehicleOwner> updateOwner(UUID id, VehicleOwner updatedOwner) {

    Optional<VehicleOwner> existingOwner = vehicleOwnerRepository.findById(id);

    if (existingOwner.isPresent()) {

        if (vehicleOwnerRepository.existsByPhoneNumberAndIdNot(
                updatedOwner.getPhoneNumber(), id)) {

            throw new IllegalStateException("Vehicle owner phone number already exists");
        }

        VehicleOwner owner = existingOwner.get();
        owner.setFirstName(updatedOwner.getFirstName());
        owner.setLastName(updatedOwner.getLastName());
        owner.setPhoneNumber(updatedOwner.getPhoneNumber());
        owner.setEmail(updatedOwner.getEmail());

        return Optional.of(vehicleOwnerRepository.save(owner));

    } else {
        return Optional.empty();
    }
}

    // Delete a vehicle owner
    public boolean deleteOwner(UUID id) {

        if (vehicleOwnerRepository.existsById(id)) {

            vehicleOwnerRepository.deleteById(id);

            return true;
        }

        return false;
    }
}
