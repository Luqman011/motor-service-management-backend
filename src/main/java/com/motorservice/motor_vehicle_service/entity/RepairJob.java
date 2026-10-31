package com.motorservice.motor_vehicle_service.entity;
import java.time.LocalDate;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "repair_jobs")
public class RepairJob {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;

    private String description;

    private String status;

    private LocalDate startDate;

    private LocalDate expectedCompletionDate;

    public RepairJob() {
    }

    public RepairJob(Vehicle vehicle, Mechanic mechanic,
                     String description, String status,
                     LocalDate startDate, LocalDate expectedCompletionDate) {
        this.vehicle = vehicle;
        this.mechanic = mechanic;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.expectedCompletionDate = expectedCompletionDate;
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpectedCompletionDate() {
        return expectedCompletionDate;
    }

    public void setExpectedCompletionDate(LocalDate expectedCompletionDate) {
        this.expectedCompletionDate = expectedCompletionDate;
    }
}
