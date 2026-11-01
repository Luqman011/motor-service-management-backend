package com.motorservice.motor_vehicle_service.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.motorservice.motor_vehicle_service.entity.Mechanic;
import com.motorservice.motor_vehicle_service.entity.RepairJob;
import com.motorservice.motor_vehicle_service.entity.Vehicle;
import com.motorservice.motor_vehicle_service.enums.RepairJobStatus;
import com.motorservice.motor_vehicle_service.repository.MechanicRepository;
import com.motorservice.motor_vehicle_service.repository.RepairJobRepository;
import com.motorservice.motor_vehicle_service.repository.VehicleRepository;

@Service
public class RepairJobService {

    private final RepairJobRepository repairJobRepository;
    private final VehicleRepository vehicleRepository;
    private final MechanicRepository mechanicRepository;

    public RepairJobService(
            RepairJobRepository repairJobRepository,
            VehicleRepository vehicleRepository,
            MechanicRepository mechanicRepository) {

        this.repairJobRepository = repairJobRepository;
        this.vehicleRepository = vehicleRepository;
        this.mechanicRepository = mechanicRepository;
    }

    // Register a repair job
    public RepairJob saveRepairJob(RepairJob repairJob) {

        UUID vehicleId = repairJob.getVehicle().getId();
        UUID mechanicId = repairJob.getMechanic().getId();

        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);
        Optional<Mechanic> mechanicOptional = mechanicRepository.findById(mechanicId);

        if (vehicleOptional.isEmpty()) {
            throw new IllegalArgumentException("Vehicle not found");
        }

        if (mechanicOptional.isEmpty()) {
            throw new IllegalArgumentException("Mechanic not found");
        }
        
        if (repairJob.getStartDate().isBefore(LocalDate.now())) {
        throw new IllegalArgumentException("Repair job start date cannot be in the past");
    }

        repairJob.setVehicle(vehicleOptional.get());
        repairJob.setMechanic(mechanicOptional.get());
        repairJob.setStatus(RepairJobStatus.PENDING);
        return repairJobRepository.save(repairJob);
    }

    // Get all repair jobs
    public List<RepairJob> getAllRepairJobs() {
        return repairJobRepository.findAll();
    }

    // Get repair job by ID
    public Optional<RepairJob> getRepairJobById(UUID id) {
        return repairJobRepository.findById(id);
    }

    // Get repair jobs by vehicle
    public List<RepairJob> getRepairJobsByVehicle(UUID vehicleId) {

        if (!vehicleRepository.existsById(vehicleId)) {
            throw new IllegalArgumentException("Vehicle not found");
        }

        return repairJobRepository.findByVehicle_Id(vehicleId);
    }

    // Get repair jobs by mechanic
    public List<RepairJob> getRepairJobsByMechanic(UUID mechanicId) {

        if (!mechanicRepository.existsById(mechanicId)) {
            throw new IllegalArgumentException("Mechanic not found");
        }

        return repairJobRepository.findByMechanic_Id(mechanicId);
    }

    //update repair job
    public Optional<RepairJob> updateRepairJob(
        UUID id, RepairJob updatedRepairJob) {

    Optional<RepairJob> repairJobOptional =
            repairJobRepository.findById(id);

    if (repairJobOptional.isPresent()) {

        Optional<Vehicle> vehicleOptional =
                vehicleRepository.findById(updatedRepairJob.getVehicle().getId());

        Optional<Mechanic> mechanicOptional =
                mechanicRepository.findById(updatedRepairJob.getMechanic().getId());

        if (vehicleOptional.isEmpty() || mechanicOptional.isEmpty()) {
            return Optional.empty();
        }

        RepairJob repairJob = repairJobOptional.get();

        repairJob.setVehicle(vehicleOptional.get());
        repairJob.setMechanic(mechanicOptional.get());
        repairJob.setDescription(updatedRepairJob.getDescription());
        repairJob.setStartDate(updatedRepairJob.getStartDate());
        repairJob.setExpectedCompletionDate(updatedRepairJob.getExpectedCompletionDate());

        return Optional.of(repairJobRepository.save(repairJob));
    }

    return Optional.empty();
}

    // patch repair job status
    public Optional<RepairJob> updateRepairJobStatus(
        UUID id, RepairJobStatus newStatus) {

    Optional<RepairJob> repairJobOptional = repairJobRepository.findById(id);

    if (repairJobOptional.isEmpty()) {
        return Optional.empty();
    }

    RepairJob repairJob = repairJobOptional.get();
    RepairJobStatus currentStatus = repairJob.getStatus();

    if (currentStatus == RepairJobStatus.PENDING) {

        if (newStatus == RepairJobStatus.IN_PROGRESS || newStatus == RepairJobStatus.CANCELLED) {

            repairJob.setStatus(newStatus);
            return Optional.of(repairJobRepository.save(repairJob));
        }

    } else if (currentStatus == RepairJobStatus.IN_PROGRESS) {

        if (newStatus == RepairJobStatus.COMPLETED || newStatus == RepairJobStatus.CANCELLED) {

            repairJob.setStatus(newStatus);
            return Optional.of(repairJobRepository.save(repairJob));
        }
    }

    return Optional.empty();
}

    // Delete repair job
    public boolean deleteRepairJob(UUID id) {

        Optional<RepairJob> repairJobOptional = repairJobRepository.findById(id);

        if (repairJobOptional.isPresent()) {
            repairJobRepository.delete(repairJobOptional.get());
            return true;
        }

        return false;
    }


    public List<RepairJob> getRepairJobsByStatus(RepairJobStatus status) {
    return repairJobRepository.findByStatusOrderByStartDateAsc(status);
}

    public List<RepairJob> getRepairJobsByDateRange(LocalDate startDate, LocalDate endDate) {

    return repairJobRepository.findByStartDateBetween(startDate, endDate);
}

}
