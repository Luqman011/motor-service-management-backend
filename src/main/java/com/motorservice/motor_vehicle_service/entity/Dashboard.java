package com.motorservice.motor_vehicle_service.entity;
public class Dashboard {

    private long totalOwners;
    private long totalVehicles;
    private long totalMechanics;
    private long totalRepairJobs;
    private long pendingJobs;
    private long inProgressJobs;
    private long completedJobs;

    public Dashboard() {
    }

    public Dashboard(long totalOwners, long totalVehicles,
                     long totalMechanics, long totalRepairJobs,
                     long pendingJobs, long inProgressJobs,
                     long completedJobs) {

        this.totalOwners = totalOwners;
        this.totalVehicles = totalVehicles;
        this.totalMechanics = totalMechanics;
        this.totalRepairJobs = totalRepairJobs;
        this.pendingJobs = pendingJobs;
        this.inProgressJobs = inProgressJobs;
        this.completedJobs = completedJobs;
    }

    public long getTotalOwners() {
        return totalOwners;
    }

    public void setTotalOwners(long totalOwners) {
        this.totalOwners = totalOwners;
    }

    public long getTotalVehicles() {
        return totalVehicles;
    }

    public void setTotalVehicles(long totalVehicles) {
        this.totalVehicles = totalVehicles;
    }

    public long getTotalMechanics() {
        return totalMechanics;
    }

    public void setTotalMechanics(long totalMechanics) {
        this.totalMechanics = totalMechanics;
    }

    public long getTotalRepairJobs() {
        return totalRepairJobs;
    }

    public void setTotalRepairJobs(long totalRepairJobs) {
        this.totalRepairJobs = totalRepairJobs;
    }

    public long getPendingJobs() {
        return pendingJobs;
    }

    public void setPendingJobs(long pendingJobs) {
        this.pendingJobs = pendingJobs;
    }

    public long getInProgressJobs() {
        return inProgressJobs;
    }

    public void setInProgressJobs(long inProgressJobs) {
        this.inProgressJobs = inProgressJobs;
    }

    public long getCompletedJobs() {
        return completedJobs;
    }

    public void setCompletedJobs(long completedJobs) {
        this.completedJobs = completedJobs;
    }
}
