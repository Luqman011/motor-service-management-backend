package com.motorservice.motor_vehicle_service.service;
import com.motorservice.motor_vehicle_service.entity.Dashboard;
import com.motorservice.motor_vehicle_service.entity.RepairJob;
import com.motorservice.motor_vehicle_service.repository.VehicleOwnerRepository;
import com.motorservice.motor_vehicle_service.repository.VehicleRepository;
import com.motorservice.motor_vehicle_service.repository.MechanicRepository;
import com.motorservice.motor_vehicle_service.repository.RepairJobRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DashboardService {

    private final VehicleOwnerRepository vehicleOwnerRepository;
    private final VehicleRepository vehicleRepository;
    private final MechanicRepository mechanicRepository;
    private final RepairJobRepository repairJobRepository;

    public DashboardService(
            VehicleOwnerRepository vehicleOwnerRepository,
            VehicleRepository vehicleRepository,
            MechanicRepository mechanicRepository,
            RepairJobRepository repairJobRepository) {

        this.vehicleOwnerRepository = vehicleOwnerRepository;
        this.vehicleRepository = vehicleRepository;
        this.mechanicRepository = mechanicRepository;
        this.repairJobRepository = repairJobRepository;
    }

    public Dashboard getDashboard() {

        Dashboard dashboard = new Dashboard();

        // Get total records
        dashboard.setTotalOwners(vehicleOwnerRepository.findAll().size());
        dashboard.setTotalVehicles(vehicleRepository.findAll().size());
        dashboard.setTotalMechanics(mechanicRepository.findAll().size());

        // Get all repair jobs
        List<RepairJob> repairJobs = repairJobRepository.findAll();

        dashboard.setTotalRepairJobs(repairJobs.size());

        // Prepare counters
        long pendingJobs = 0;
        long inProgressJobs = 0;
        long completedJobs = 0;

        // Count jobs according to their status
        for (RepairJob repairJob : repairJobs) {

            if ("PENDING".equalsIgnoreCase(repairJob.getStatus())) {
                pendingJobs++;
            }

            if ("IN_PROGRESS".equalsIgnoreCase(repairJob.getStatus())) {
                inProgressJobs++;
            }

            if ("COMPLETED".equalsIgnoreCase(repairJob.getStatus())) {
                completedJobs++;
            }
        }

        dashboard.setPendingJobs(pendingJobs);
        dashboard.setInProgressJobs(inProgressJobs);
        dashboard.setCompletedJobs(completedJobs);

        return dashboard;
    }
}