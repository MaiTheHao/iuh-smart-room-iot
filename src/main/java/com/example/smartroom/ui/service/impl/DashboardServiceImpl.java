package com.example.smartroom.ui.service.impl;

import com.example.smartroom.modules.device.entity.Device;
import com.example.smartroom.modules.device.repository.DeviceRepository;
import com.example.smartroom.modules.sensor.entity.Sensor;
import com.example.smartroom.modules.sensor_reading.entity.SensorReading;
import com.example.smartroom.modules.sensor_reading.repository.SensorReadingRepository;
import com.example.smartroom.ui.dto.DeviceWithSensorsDto;
import com.example.smartroom.ui.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {
    
    private final DeviceRepository deviceRepository;
    private final SensorReadingRepository sensorReadingRepository;
    
    @Override
    public List<DeviceWithSensorsDto> getAllDevicesWithSensors(String langCode) {
        try {
            List<Device> devices = deviceRepository.findAll();
            
            return devices.stream()
                .map(device -> mapToDeviceWithSensorsDto(device, langCode))
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching devices with sensors: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    private Double getAverageWattHour(Long deviceId, int hours) {
        Device device = deviceRepository.findById(deviceId).orElse(null);
        if (device == null || device.getSensors().isEmpty()) {
            return null;
        }
        
        Instant endTime = Instant.now();
        Instant startTime = endTime.minus(hours, ChronoUnit.HOURS);
        
        List<Long> sensorIds = device.getSensors().stream()
            .map(Sensor::getId)
            .collect(Collectors.toList());
        
        Pageable pageable = PageRequest.of(0, 1000);
        List<SensorReading> readings = sensorReadingRepository
            .findBySensorIdsAndTimestampBetween(sensorIds, startTime, endTime, pageable)
            .getContent();
        
        return readings.stream()
            .filter(sr -> sr.getWattHour() != null)
            .mapToDouble(SensorReading::getWattHour)
            .average()
            .orElse(0.0);
    }
    
    private String getDeviceStatus(Long deviceId, int minutesThreshold) {
        Device device = deviceRepository.findById(deviceId).orElse(null);
        if (device == null || device.getSensors().isEmpty()) {
            return "Offline";
        }
        
        Instant threshold = Instant.now().minus(minutesThreshold, ChronoUnit.MINUTES);
        
        List<Long> sensorIds = device.getSensors().stream()
            .map(Sensor::getId)
            .collect(Collectors.toList());
        
        Pageable pageable = PageRequest.of(0, 1);
        List<SensorReading> recentReadings = sensorReadingRepository
            .findBySensorIdsAndTimestampBetween(
                sensorIds, 
                threshold, 
                Instant.now(), 
                pageable
            ).getContent();
        
        if (recentReadings.isEmpty()) {
            return "Offline";
        }
        
        boolean hasActivePower = recentReadings.stream()
            .anyMatch(sr -> sr.getWatt() != null && sr.getWatt() > 10.0);
        
        return hasActivePower ? "Online" : "Idle";
    }
    
    private DeviceWithSensorsDto mapToDeviceWithSensorsDto(Device device, String langCode) {
        try {
            String deviceName = device.getDeviceLans() != null ?
                device.getDeviceLans().stream()
                    .filter(dl -> dl.getLanguage() != null && langCode.equals(dl.getLanguage().getCode()))
                    .findFirst()
                    .map(dl -> dl.getName())
                    .orElse(device.getModel()) : device.getModel();
            
            String roomName = null;
            String floorLevel = null;
            
            if (device.getRoom() != null && device.getRoom().getRoomLans() != null) {
                roomName = device.getRoom().getRoomLans().stream()
                    .filter(rl -> rl.getLanguage() != null && langCode.equals(rl.getLanguage().getCode()))
                    .findFirst()
                    .map(rl -> rl.getName())
                    .orElse("Unknown Room");
                
                if (device.getRoom().getFloor() != null && device.getRoom().getFloor().getFloorLans() != null) {
                    floorLevel = device.getRoom().getFloor().getFloorLans().stream()
                        .filter(fl -> fl.getLanguage() != null && langCode.equals(fl.getLanguage().getCode()))
                        .findFirst()
                        .map(fl -> fl.getName())
                        .orElse(device.getRoom().getFloor().getLevel() != null ? 
                                String.valueOf(device.getRoom().getFloor().getLevel()) : "N/A");
                }
            }
            
            List<DeviceWithSensorsDto.SensorInfoDto> sensors = device.getSensors() != null ?
                device.getSensors().stream()
                    .map(sensor -> {
                        try {
                            String sensorName = sensor.getSensorLans() != null ?
                                sensor.getSensorLans().stream()
                                    .filter(sl -> sl.getLanguage() != null && langCode.equals(sl.getLanguage().getCode()))
                                    .findFirst()
                                    .map(sl -> sl.getName())
                                    .orElse(sensor.getModel()) : sensor.getModel();
                            
                            String sensorType = "Unknown";
                            if (sensor.getSensorType() != null && sensor.getSensorType().getSensorTypeLans() != null) {
                                sensorType = sensor.getSensorType().getSensorTypeLans().stream()
                                    .filter(stl -> stl.getLanguage() != null && langCode.equals(stl.getLanguage().getCode()))
                                    .findFirst()
                                    .map(stl -> stl.getName())
                                    .orElse("Unknown");
                            }
                            
                            return DeviceWithSensorsDto.SensorInfoDto.builder()
                                .id(sensor.getId())
                                .name(sensorName)
                                .model(sensor.getModel())
                                .type(sensorType)
                                .sensorTypeId(sensor.getSensorType() != null ? sensor.getSensorType().getId() : null)
                                .build();
                        } catch (Exception e) {
                            log.warn("Error mapping sensor {}: {}", sensor.getId(), e.getMessage());
                            return null;
                        }
                    })
                    .filter(s -> s != null)
                    .collect(Collectors.toList()) : new ArrayList<>();
            
            Double avgWattHour = null;
            String status = "Offline";
            
            try {
                avgWattHour = getAverageWattHour(device.getId(), 24);
                status = getDeviceStatus(device.getId(), 5);
            } catch (Exception e) {
                log.warn("Error calculating stats for device {}: {}", device.getId(), e.getMessage());
            }
            
            return DeviceWithSensorsDto.builder()
                .id(device.getId())
                .name(deviceName)
                .model(device.getModel())
                .mac(device.getMac())
                .ip(device.getIp())
                .isGateway(device.getIsGateway())
                .connectionProtocol(device.getConnectionProtocol())
            .roomId(device.getRoom() != null ? device.getRoom().getId() : null)
            .roomName(roomName)
            .floorLevel(floorLevel)
            .sensors(sensors)
            .avgWattHour(avgWattHour)
            .status(status)
            .build();
        } catch (Exception e) {
            log.error("Error mapping device {}: {}", device.getId(), e.getMessage(), e);
            return DeviceWithSensorsDto.builder()
                .id(device.getId())
                .name(device.getModel())
                .model(device.getModel())
                .sensors(new ArrayList<>())
                .status("Offline")
                .build();
        }
    }
}
