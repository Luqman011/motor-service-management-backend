package com.motorservice.motor_vehicle_service.entity;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "spare_parts")
public class SparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "repair_job_id")
    private RepairJob repairJob;

    private String partName;

    private String description;

    private Integer quantity;

    public SparePart() {
    }

    public SparePart(RepairJob repairJob, String partName,
                     String description, Integer quantity) {
        this.repairJob = repairJob;
        this.partName = partName;
        this.description = description;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RepairJob getRepairJob() {
        return repairJob;
    }

    public void setRepairJob(RepairJob repairJob) {
        this.repairJob = repairJob;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
