package com.example.smartroom.ui.service;

import com.example.smartroom.ui.dto.DeviceWithSensorsDto;

import java.util.List;

public interface DashboardService {
    
    List<DeviceWithSensorsDto> getAllDevicesWithSensors(String langCode);
}
