package com.motorservice.motor_vehicle_service.service;
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

    // Update repair job
    public Optional<RepairJob> updateRepairJob(UUID id, RepairJob updatedRepairJob) {

        Optional<RepairJob> repairJobOptional = repairJobRepository.findById(id);

        if (repairJobOptional.isPresent()) {

            UUID vehicleId = updatedRepairJob.getVehicle().getId();
            UUID mechanicId = updatedRepairJob.getMechanic().getId();

            Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);
            Optional<Mechanic> mechanicOptional = mechanicRepository.findById(mechanicId);

            if (vehicleOptional.isEmpty()) {
                throw new IllegalArgumentException("Vehicle not found");
            }

            if (mechanicOptional.isEmpty()) {
                throw new IllegalArgumentException("Mechanic not found");
            }

            RepairJob existingRepairJob = repairJobOptional.get();

            existingRepairJob.setVehicle(vehicleOptional.get());
            existingRepairJob.setMechanic(mechanicOptional.get());
            existingRepairJob.setDescription(updatedRepairJob.getDescription());
            existingRepairJob.setStatus(updatedRepairJob.getStatus());
            existingRepairJob.setStartDate(updatedRepairJob.getStartDate());
            existingRepairJob.setExpectedCompletionDate(updatedRepairJob.getExpectedCompletionDate());

            return Optional.of(repairJobRepository.save(existingRepairJob));
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
}
