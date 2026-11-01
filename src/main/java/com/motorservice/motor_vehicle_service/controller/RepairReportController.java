package com.motorservice.motor_vehicle_service.controller;
import com.motorservice.motor_vehicle_service.entity.RepairReport;
import com.motorservice.motor_vehicle_service.service.RepairReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/repair-reports")
public class RepairReportController {

    @Autowired
    private RepairReportService repairReportService;

    // Create a repair report
    @PostMapping
    public ResponseEntity<String> createRepairReport(
            @RequestBody RepairReport repairReport) {

        String result = repairReportService.createRepairReport(repairReport);

        if (result.equals("Repair job not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.equals("Cannot create report for an incomplete repair job")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }

    // Get all repair reports
    @GetMapping
    public ResponseEntity<List<RepairReport>> getAllRepairReports() {
        return ResponseEntity.ok(repairReportService.getAllRepairReports());
    }

    // Get repair report by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRepairReportById(@PathVariable UUID id) {

        Optional<RepairReport> repairReport = repairReportService.getRepairReportById(id);

        if (repairReport.isPresent()) {
            return ResponseEntity.ok(repairReport.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Repair report not found");
        }
    }
}