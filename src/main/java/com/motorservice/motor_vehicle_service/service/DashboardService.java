package com.motorservice.motor_vehicle_service.service;
import com.motorservice.motor_vehicle_service.entity.Dashboard;
import com.motorservice.motor_vehicle_service.entity.RepairJob;
import com.motorservice.motor_vehicle_service.enums.RepairJobStatus;
import com.motorservice.motor_vehicle_service.repository.VehicleOwnerRepository;
import com.motorservice.motor_vehicle_service.repository.VehicleRepository;
import com.motorservice.motor_vehicle_service.repository.MechanicRepository;
import com.motorservice.motor_vehicle_service.repository.RepairJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired 
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

            if (repairJob.getStatus() == RepairJobStatus.PENDING) {
            pendingJobs++;
}

            if (repairJob.getStatus() == RepairJobStatus.IN_PROGRESS) {
            inProgressJobs++;
}

            if (repairJob.getStatus() == RepairJobStatus.COMPLETED) {
            completedJobs++;
}
        }

        dashboard.setPendingJobs(pendingJobs);
        dashboard.setInProgressJobs(inProgressJobs);
        dashboard.setCompletedJobs(completedJobs);

        return dashboard;
    }


    public Map<String, Long> getDashboardSummary() {

    Map<String, Long> dashboard = new HashMap<>();

    dashboard.put("totalOwners", vehicleOwnerRepository.count());
    dashboard.put("totalVehicles", vehicleRepository.count());
    dashboard.put("totalMechanics", mechanicRepository.count());
    dashboard.put("totalRepairJobs", repairJobRepository.count());

    dashboard.put("pendingJobs",
            repairJobRepository.countByStatus(RepairJobStatus.PENDING));

    dashboard.put("inProgressJobs",
            repairJobRepository.countByStatus(RepairJobStatus.IN_PROGRESS));

    dashboard.put("completedJobs",
            repairJobRepository.countByStatus(RepairJobStatus.COMPLETED));

    dashboard.put("cancelledJobs",
            repairJobRepository.countByStatus(RepairJobStatus.CANCELLED));

    return dashboard;
}
}