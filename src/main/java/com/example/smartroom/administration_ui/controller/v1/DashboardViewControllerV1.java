package com.example.smartroom.administration_ui.controller.v1;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.smartroom.administration_ui.constants.ViewTemplatePathConstants;
import com.example.smartroom.administration_ui.service.DashboardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("administration/dashboard")
@RequiredArgsConstructor
public class DashboardViewControllerV1 {

    private final DashboardService dashboardService;

    @GetMapping
    public String getDashboard(
            Model model,
            @RequestParam(defaultValue = "0") int roomPage,
            @RequestParam(defaultValue = "10") int roomSize,
            @RequestParam(defaultValue = "0") int hubPage,
            @RequestParam(defaultValue = "10") int hubSize,
            @RequestParam(defaultValue = "0") int devicePage,
            @RequestParam(defaultValue = "10") int deviceSize,
            @RequestParam(defaultValue = "0") int sensorPage,
            @RequestParam(defaultValue = "10") int sensorSize
    ) {
        Pageable roomPageable = PageRequest.of(roomPage, roomSize);
        Pageable hubPageable = PageRequest.of(hubPage, hubSize);
        Pageable devicePageable = PageRequest.of(devicePage, deviceSize);
        Pageable sensorPageable = PageRequest.of(sensorPage, sensorSize);

        model.addAttribute("dashboardData", dashboardService.getDashboardData(roomPageable, hubPageable, devicePageable, sensorPageable));
        return ViewTemplatePathConstants.DASHBOARD_INDEX;
    }
}
