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
import com.motorservice.motor_vehicle_service.entity.RepairJob;
import com.motorservice.motor_vehicle_service.service.RepairJobService;

@RestController
@RequestMapping("/api/repair-jobs")
public class RepairJobController {

    private final RepairJobService repairJobService;

    public RepairJobController(RepairJobService repairJobService) {
        this.repairJobService = repairJobService;
    }

    // Register repair job
    @PostMapping
    public ResponseEntity<?> saveRepairJob(@RequestBody RepairJob repairJob) {

        try {
            repairJobService.saveRepairJob(repairJob);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Repair job registered successfully");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Get all repair jobs
    @GetMapping
    public ResponseEntity<List<RepairJob>> getAllRepairJobs() {
        return ResponseEntity.ok(repairJobService.getAllRepairJobs());
    }

    // Get repair job by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRepairJobById(@PathVariable UUID id) {

        Optional<RepairJob> repairJobOptional = repairJobService.getRepairJobById(id);

        if (repairJobOptional.isPresent()) {
            return ResponseEntity.ok(repairJobOptional.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Repair job not found");
    }

    // Get repair jobs by vehicle
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<?> getRepairJobsByVehicle(@PathVariable UUID vehicleId) {

        try {
            return ResponseEntity.ok(repairJobService.getRepairJobsByVehicle(vehicleId));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Get repair jobs by mechanic
    @GetMapping("/mechanic/{mechanicId}")
    public ResponseEntity<?> getRepairJobsByMechanic(@PathVariable UUID mechanicId) {

        try {
            return ResponseEntity.ok( repairJobService.getRepairJobsByMechanic(mechanicId));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Update repair job
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRepairJob(
            @PathVariable UUID id,
            @RequestBody RepairJob repairJob) {

        try {
            Optional<RepairJob> updatedRepairJob =
                    repairJobService.updateRepairJob(id, repairJob);

            if (updatedRepairJob.isPresent()) {
                return ResponseEntity.ok("Repair job updated successfully");
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Repair job not found");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Delete repair job
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRepairJob(@PathVariable UUID id) {

        boolean deleted = repairJobService.deleteRepairJob(id);

        if (deleted) {
            return ResponseEntity.ok("Repair job deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Repair job not found");
    }
}
