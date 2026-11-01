package com.motorservice.motor_vehicle_service.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.UUID;
import java.time.LocalDate;

@Entity
@Table(name = "repair_reports")
public class RepairReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    private LocalDate reportDate;

    @ManyToOne
    @JoinColumn(name = "repair_job_id")
    private RepairJob repairJob;

    // Default constructor
    public RepairReport() {
    }

    // Parameterized constructor
    public RepairReport(UUID id, String title, String description,
                        LocalDate reportDate, RepairJob repairJob) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reportDate = reportDate;
        this.repairJob = repairJob;
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public RepairJob getRepairJob() {
        return repairJob;
    }

    public void setRepairJob(RepairJob repairJob) {
        this.repairJob = repairJob;
    }
}