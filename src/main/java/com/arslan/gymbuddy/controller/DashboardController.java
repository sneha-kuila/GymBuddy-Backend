package com.arslan.gymbuddy.controller;

import com.arslan.gymbuddy.dto.DashboardDto;
import com.arslan.gymbuddy.entity.User;
import com.arslan.gymbuddy.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardDto> getDashboard(@AuthenticationPrincipal User user) {
        DashboardDto dashboard = dashboardService.getDashboardData(user.getId());
        return ResponseEntity.ok(dashboard);
    }
}