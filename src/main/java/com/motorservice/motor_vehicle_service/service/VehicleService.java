package com.motorservice.motor_vehicle_service.service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.motorservice.motor_vehicle_service.entity.Vehicle;
import com.motorservice.motor_vehicle_service.entity.VehicleOwner;
import com.motorservice.motor_vehicle_service.repository.VehicleRepository;
import com.motorservice.motor_vehicle_service.repository.VehicleOwnerRepository;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleOwnerRepository vehicleOwnerRepository;

    public VehicleService(VehicleRepository vehicleRepository,
                          VehicleOwnerRepository vehicleOwnerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleOwnerRepository = vehicleOwnerRepository;
    }

    // Register a vehicle
    public Vehicle saveVehicle(Vehicle vehicle) {

        if (vehicleRepository.existsByRegistrationNumber(
                vehicle.getRegistrationNumber())) {
            throw new IllegalStateException(
                    "Vehicle registration number already exists");
        }

        UUID ownerId = vehicle.getOwner().getId();

        VehicleOwner owner = vehicleOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Vehicle owner not found"));

        vehicle.setOwner(owner);

        return vehicleRepository.save(vehicle);
    }

    // Retrieve all vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Retrieve a vehicle by ID
    public Optional<Vehicle> getVehicleById(UUID id) {
        return vehicleRepository.findById(id);
    }

    // Retrieve vehicles belonging to an owner
    public List<Vehicle> getVehiclesByOwner(UUID ownerId) {

        if (!vehicleOwnerRepository.existsById(ownerId)) {
            throw new IllegalArgumentException(
                    "Vehicle owner not found");
        }

        return vehicleRepository.findByOwner_Id(ownerId);
    }

    // Update vehicle details
    public Optional<Vehicle> updateVehicle(UUID id, Vehicle updatedVehicle) {

        Optional<Vehicle> vehicleOptional =
                vehicleRepository.findById(id);

        if (vehicleOptional.isPresent()) {

            Vehicle existingVehicle = vehicleOptional.get();

            if (vehicleRepository.existsByRegistrationNumberAndIdNot(
                    updatedVehicle.getRegistrationNumber(), id)) {
                throw new IllegalStateException(
                        "Vehicle registration number already exists");
            }

            UUID ownerId = updatedVehicle.getOwner().getId();

            VehicleOwner owner = vehicleOwnerRepository.findById(ownerId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Vehicle owner not found"));

            existingVehicle.setRegistrationNumber(
                    updatedVehicle.getRegistrationNumber());
            existingVehicle.setMake(updatedVehicle.getMake());
            existingVehicle.setModel(updatedVehicle.getModel());
            existingVehicle.setYear(updatedVehicle.getYear());
            existingVehicle.setColor(updatedVehicle.getColor());
            existingVehicle.setOwner(owner);

            return Optional.of(vehicleRepository.save(existingVehicle));
        }

        return Optional.empty();
    }

    // Delete a vehicle
    public boolean deleteVehicle(UUID id) {

        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
