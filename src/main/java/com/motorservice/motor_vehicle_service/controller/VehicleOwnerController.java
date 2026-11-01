
package com.motorservice.motor_vehicle_service.controller;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.motorservice.motor_vehicle_service.entity.VehicleOwner;
import com.motorservice.motor_vehicle_service.service.VehicleOwnerService;

@RestController
@RequestMapping("/api/owners")
public class VehicleOwnerController {

    private final VehicleOwnerService vehicleOwnerService;

    public VehicleOwnerController(VehicleOwnerService vehicleOwnerService) {
        this.vehicleOwnerService = vehicleOwnerService;
    }

    // Register a vehicle owner
    @PostMapping
    public ResponseEntity<String> saveOwner(
        @RequestBody VehicleOwner vehicleOwner) {

    try {
        vehicleOwnerService.saveOwner(vehicleOwner);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vehicle owner registered successfully");
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
    

    // Retrieve all vehicle owners
   @GetMapping
    public ResponseEntity<List<VehicleOwner>> getAllOwners() {

    List<VehicleOwner> owners = vehicleOwnerService.getAllOwners();

    return ResponseEntity.ok(owners);
}


    // Retrieve one vehicle owner by ID
   @GetMapping("/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable UUID id) {

    Optional<VehicleOwner> ownerOptional = vehicleOwnerService.getOwnerById(id);

    if (ownerOptional.isPresent()) {
        return ResponseEntity.ok(ownerOptional.get());
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle owner not found");
}

    @GetMapping("/vehicle-make/{make}")
    public ResponseEntity<List<VehicleOwner>> getOwnersByVehicleMake(
        @PathVariable String make) {

    return ResponseEntity.ok(vehicleOwnerService.getOwnersByVehicleMake(make));
}

    // Update a vehicle owner
    @PutMapping("/{id}")
    public ResponseEntity<String> updateOwner(
        @PathVariable UUID id,
        @RequestBody VehicleOwner updatedOwner) {

    try {
        Optional<VehicleOwner> owner = vehicleOwnerService.updateOwner(id, updatedOwner);

        if (owner.isPresent()) {
            return ResponseEntity.ok("Vehicle owner updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle owner not found");
        }

    } catch (IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}

    // Delete a vehicle owner
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOwner(@PathVariable UUID id) {

    boolean deleted = vehicleOwnerService.deleteOwner(id);

    if (deleted) {
        return ResponseEntity.ok("Vehicle owner deleted successfully");
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle owner not found. Deletion failed");
}
    
}
