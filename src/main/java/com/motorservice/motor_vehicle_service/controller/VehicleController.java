package com.motorservice.motor_vehicle_service.controller;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.motorservice.motor_vehicle_service.entity.Vehicle;
import com.motorservice.motor_vehicle_service.service.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // Register a vehicle
    @PostMapping
public ResponseEntity<String> saveVehicle(
        @RequestBody Vehicle vehicle) {

    try {
        vehicleService.saveVehicle(vehicle);

        return ResponseEntity.status(HttpStatus.CREATED).body("Vehicle registered successfully");

    } catch (IllegalStateException e) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

    } catch (IllegalArgumentException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}


    // Get all vehicles
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    // Retrieve all vehicles
    @GetMapping("/{id}")
public ResponseEntity<?> getVehicleById(@PathVariable UUID id) {

    Optional<Vehicle> vehicleOptional = vehicleService.getVehicleById(id);

    if (vehicleOptional.isPresent()) {
        return ResponseEntity.ok(vehicleOptional.get());
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
}

    // Retrieve all vehicles belonging to an owner
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getVehiclesByOwner(
            @PathVariable UUID ownerId) {

        try {
            List<Vehicle> vehicles = vehicleService.getVehiclesByOwner(ownerId);

            return ResponseEntity.ok(vehicles);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Retrieve all vehicles by make

    @GetMapping("/make/{make}")
    public ResponseEntity<List<Vehicle>> getVehiclesByMake(
        @PathVariable String make) {

    return ResponseEntity.ok(vehicleService.getVehiclesByMake(make));
}

    // Update a vehicle
    @PutMapping("/{id}")
    public ResponseEntity<String> updateVehicle(
        @PathVariable UUID id,
        @RequestBody Vehicle updatedVehicle) {

    try {
        Optional<Vehicle> vehicle = vehicleService.updateVehicle(id, updatedVehicle);

        if (vehicle.isPresent()) {
            return ResponseEntity.ok("Vehicle updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
        }

    } catch (IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}

    // Delete a vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(
            @PathVariable UUID id) {

        boolean deleted = vehicleService.deleteVehicle(id);

        if (deleted) {
            return ResponseEntity.ok(
                    "Vehicle deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found. Deletion failed");
    }
}
