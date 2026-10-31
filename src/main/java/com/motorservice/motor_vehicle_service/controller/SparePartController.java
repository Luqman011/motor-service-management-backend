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
import com.motorservice.motor_vehicle_service.entity.SparePart;
import com.motorservice.motor_vehicle_service.service.SparePartService;

@RestController
@RequestMapping("/api/spare-parts")
public class SparePartController {

    private final SparePartService sparePartService;

    public SparePartController(SparePartService sparePartService) {
        this.sparePartService = sparePartService;
    }

    // Register spare part
    @PostMapping
    public ResponseEntity<?> saveSparePart(@RequestBody SparePart sparePart) {

        try {
            sparePartService.saveSparePart(sparePart);

            return ResponseEntity.status(HttpStatus.CREATED).body("Spare part registered successfully");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Get all spare parts
    @GetMapping
    public ResponseEntity<List<SparePart>> getAllSpareParts() {
        return ResponseEntity.ok(sparePartService.getAllSpareParts());
    }

    // Get spare part by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSparePartById(@PathVariable UUID id) {

        Optional<SparePart> sparePartOptional =
                sparePartService.getSparePartById(id);

        if (sparePartOptional.isPresent()) {
            return ResponseEntity.ok(sparePartOptional.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Spare part not found");
    }

    // Get spare parts by repair job
    @GetMapping("/repair-job/{repairJobId}")
    public ResponseEntity<?> getSparePartsByRepairJob(
            @PathVariable UUID repairJobId) {

        try {
            return ResponseEntity.ok(
                    sparePartService.getSparePartsByRepairJob(repairJobId));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Update spare part
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSparePart(
            @PathVariable UUID id,
            @RequestBody SparePart sparePart) {

        try {
            Optional<SparePart> updatedSparePart = sparePartService.updateSparePart(id, sparePart);

            if (updatedSparePart.isPresent()) {
                return ResponseEntity.ok("Spare part updated successfully");
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Spare part not found");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Delete spare part
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSparePart(@PathVariable UUID id) {

        boolean deleted = sparePartService.deleteSparePart(id);

        if (deleted) {
            return ResponseEntity.ok("Spare part deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Spare part not found");
    }
}
