package com.example.smartroom.ui.controller;

import com.example.smartroom.ui.dto.DeviceWithSensorsDto;
import com.example.smartroom.ui.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/administration/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {
    
    private final DashboardService dashboardService;

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "vi") String langCode,
            Model model) {
        
        log.info("Rendering dashboard page for language: {}", langCode);
        
        List<DeviceWithSensorsDto> devices = dashboardService.getAllDevicesWithSensors(langCode);
        
        long totalDevices = devices.size();
        long totalSensors = devices.stream()
            .mapToLong(d -> d.getSensors() != null ? d.getSensors().size() : 0)
            .sum();
        long onlineDevices = devices.stream()
            .filter(d -> "Online".equals(d.getStatus()))
            .count();
        
        Double totalAvgWattHour = devices.stream()
            .filter(d -> d.getAvgWattHour() != null)
            .mapToDouble(DeviceWithSensorsDto::getAvgWattHour)
            .sum();
        
        Instant endTime = Instant.now();
        Instant startTime = endTime.minus(7, ChronoUnit.DAYS);
        
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("pageDescription", "Thống kê và giám sát hệ thống");
        model.addAttribute("langCode", langCode);
        
        model.addAttribute("totalDevices", totalDevices);
        model.addAttribute("totalSensors", totalSensors);
        model.addAttribute("onlineDevices", onlineDevices);
        model.addAttribute("totalAvgWattHour", String.format("%.2f", totalAvgWattHour));
        
        model.addAttribute("devices", devices);
        
        model.addAttribute("defaultStartTime", startTime.toString());
        model.addAttribute("defaultEndTime", endTime.toString());
        
        return "pages/dashboard/index-v2";
    }
}
