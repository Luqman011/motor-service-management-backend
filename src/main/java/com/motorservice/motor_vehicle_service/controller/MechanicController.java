package com.motorservice.motor_vehicle_service.controller;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.motorservice.motor_vehicle_service.entity.Mechanic;
import com.motorservice.motor_vehicle_service.service.MechanicService;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicController {

    private final MechanicService mechanicService;

    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    // Register a mechanic
    @PostMapping
    public ResponseEntity<?> saveMechanic(@RequestBody Mechanic mechanic) {

        try {
            mechanicService.saveMechanic(mechanic);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Mechanic registered successfully");

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Get all mechanics
    @GetMapping
    public ResponseEntity<List<Mechanic>> getAllMechanics() {
        return ResponseEntity.ok(mechanicService.getAllMechanics());
    }

    // Get mechanic by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMechanicById(@PathVariable UUID id) {

        Optional<Mechanic> mechanicOptional = mechanicService.getMechanicById(id);

        if (mechanicOptional.isPresent()) {
            return ResponseEntity.ok(mechanicOptional.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mechanic not found");
    }

    @GetMapping("/without-repair-jobs")
    public ResponseEntity<List<Mechanic>> getMechanicsWithoutRepairJobs() {
    return ResponseEntity.ok(mechanicService.getMechanicsWithoutRepairJobs());
}
    // Update mechanic
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMechanic(
            @PathVariable UUID id,
            @RequestBody Mechanic mechanic) {

        try {
            Optional<Mechanic> updatedMechanic = mechanicService.updateMechanic(id, mechanic);

            if (updatedMechanic.isPresent()) {
                return ResponseEntity.ok("Mechanic updated successfully");
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mechanic not found");

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Delete mechanic
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMechanic(@PathVariable UUID id) {

        boolean deleted = mechanicService.deleteMechanic(id);

        if (deleted) {
            return ResponseEntity.ok("Mechanic deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mechanic not found");
    }
}
