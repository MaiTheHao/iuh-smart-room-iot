package com.example.smartroom.administration_ui.service;

import org.springframework.data.domain.Pageable;

import com.example.smartroom.administration_ui.dto.dashboard.DashboardViewDataDTO;

public interface DashboardService {
    DashboardViewDataDTO getDashboardData(Pageable roomPageable, Pageable hubPageable, Pageable devicePageable, Pageable sensorPageable);
    DashboardViewDataDTO getDashboardData(Pageable pageable);
}
