package com.motorservice.motor_vehicle_service.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.motorservice.motor_vehicle_service.entity.RepairJob;
import com.motorservice.motor_vehicle_service.enums.RepairJobStatus;
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
    public ResponseEntity<String> saveRepairJob(@RequestBody RepairJob repairJob) {
    try {
        repairJobService.saveRepairJob(repairJob);
        return ResponseEntity.status(HttpStatus.CREATED).body("Repair job created successfully");
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    catch (IllegalStateException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
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

    // Get repair jobs by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RepairJob>> getRepairJobsByStatus(
        @PathVariable RepairJobStatus status) {

    return ResponseEntity.ok(repairJobService.getRepairJobsByStatus(status));
}

    // Get repair jobs by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<RepairJob>> getRepairJobsByDateRange(
        @RequestParam String startDate,
        @RequestParam String endDate) {

    LocalDate start = LocalDate.parse(startDate);
    LocalDate end = LocalDate.parse(endDate);

    if (start.isAfter(end)) {
        return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(
            repairJobService.getRepairJobsByDateRange(start, end));
}

    // Update repair job
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRepairJob(
        @PathVariable UUID id,
        @RequestBody RepairJob updatedRepairJob) {

    Optional<RepairJob> repairJob =
            repairJobService.updateRepairJob(id, updatedRepairJob);

    if (repairJob.isPresent()) {
        return ResponseEntity.ok("Repair job updated successfully");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Repair job not found");
    }
}

    //patch repair job status

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateRepairJobStatus(
        @PathVariable UUID id,
        @RequestParam RepairJobStatus status) {

    Optional<RepairJob> repairJob = repairJobService.updateRepairJobStatus(id, status);

    if (repairJob.isPresent()) {
        return ResponseEntity.ok("Repair job status updated successfully");
    } else {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid status transition or repair job not found");
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
