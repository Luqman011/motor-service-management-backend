package com.motorservice.motor_vehicle_service.controller;
import com.motorservice.motor_vehicle_service.entity.Dashboard;
import com.motorservice.motor_vehicle_service.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<Dashboard> getDashboard() {

        Dashboard dashboard = dashboardService.getDashboard();

        return ResponseEntity.ok(dashboard);
    }
}