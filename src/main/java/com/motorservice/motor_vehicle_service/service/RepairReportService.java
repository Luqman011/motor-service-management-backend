package com.motorservice.motor_vehicle_service.service;
import com.motorservice.motor_vehicle_service.repository.RepairReportRepository;
import com.motorservice.motor_vehicle_service.entity.RepairJob;
import com.motorservice.motor_vehicle_service.entity.RepairReport;
import com.motorservice.motor_vehicle_service.enums.RepairJobStatus;
import com.motorservice.motor_vehicle_service.repository.RepairJobRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairReportService {

    @Autowired
    private RepairReportRepository repairReportRepository;

    @Autowired
    private RepairJobRepository repairJobRepository;

    public String createRepairReport(RepairReport repairReport) {

    Optional<RepairJob> repairJobOptional = repairJobRepository.findById(repairReport.getRepairJob().getId());

    if (repairJobOptional.isEmpty()) {
        return "Repair job not found";
    }

    RepairJob repairJob = repairJobOptional.get();

    if (repairJob.getStatus() != RepairJobStatus.COMPLETED) {
        return "Cannot create report for an incomplete repair job";
    }

    repairReport.setRepairJob(repairJob);
    repairReport.setReportDate(LocalDate.now());

    repairReportRepository.save(repairReport);

    return "Repair report created successfully";
}

    public List<RepairReport> getAllRepairReports() {
    return repairReportRepository.findAll();
}

    public Optional<RepairReport> getRepairReportById(UUID id) {
    return repairReportRepository.findById(id);
}

}