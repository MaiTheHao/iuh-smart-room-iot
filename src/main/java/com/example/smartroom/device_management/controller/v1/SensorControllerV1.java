package com.example.smartroom.device_management.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.smartroom.common.util.ApiResponse;
import com.example.smartroom.device_management.dto.sensor.SensorCreateDTO;
import com.example.smartroom.device_management.dto.sensor.SensorDTO;
import com.example.smartroom.device_management.service.SensorDataTypeService;
import com.example.smartroom.device_management.service.SensorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sensors")
@RequiredArgsConstructor
public class SensorControllerV1 {
    private final SensorService sensorService;
    private final SensorDataTypeService sensorDataTypeService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllSensors(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(required = false) String deviceId
    ) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<SensorDTO> sensors = deviceId != null
            ? sensorService.getListByDeviceId(deviceId, pageRequest)
            : sensorService.getList(pageRequest);

        ApiResponse<?> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched sensors successfully",
            sensors
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getSensorById(@PathVariable String id) {
        SensorDTO sensor = sensorService.getSensorById(id);
        ApiResponse<?> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched sensor successfully",
            sensor
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createSensor(@RequestBody @Valid SensorCreateDTO dto) {
        SensorDTO createdSensor = sensorService.createSensor(dto);

        ApiResponse<?> response = ApiResponse.success(
            HttpStatus.CREATED,
            "Sensor created successfully",
            createdSensor
        );
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteSensorById(@PathVariable String id) {
        SensorDTO deletedSensor = sensorService.deleteSensorById(id);
        ApiResponse<?> response = ApiResponse.success(
            HttpStatus.OK,
            "Deleted sensor successfully",
            deletedSensor
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/data-types")
    public ResponseEntity<ApiResponse<?>> getSensorDataTypes(
        @PathVariable String id,
        @RequestParam(required = false) int page,
        @RequestParam(required = false) int size
    ) {
        Pageable pageRequest = PageRequest.of(page, size);
        var dataTypes = sensorDataTypeService.getListBySensorId(id, pageRequest);

        ApiResponse<?> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched sensor data types successfully",
            dataTypes
        );

        return ResponseEntity.ok(response);
    }
}
