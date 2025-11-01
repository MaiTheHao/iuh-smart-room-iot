package com.example.smartroom.data_ingestion.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.smartroom.common.vo.ApiResponse;
import com.example.smartroom.data_ingestion.dto.sensor_data.SensorDataCreateDTO;
import com.example.smartroom.data_ingestion.dto.sensor_data.SensorDataDTO;
import com.example.smartroom.data_ingestion.service.SensorDataService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.time.Instant;

@RestController
@RequestMapping("/api/v1/sensor-datas")
@RequiredArgsConstructor
public class SensorDataControllerV1 {
    private final SensorDataService sensorDataService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable Long id) {
        SensorDataDTO data = sensorDataService.getById(id);
        ApiResponse<SensorDataDTO> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched sensor data successfully",
            data
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getList(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(required = false) Long sensorId,
        @RequestParam(required = false) Long dataTypeId,
        @RequestParam(required = false) Instant startTime,
        @RequestParam(required = false) Instant endTime
    ) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<SensorDataDTO> result;
        
        if (sensorId != null && startTime != null && endTime != null) {
            result = sensorDataService.getListBySensorIdAndRecordedAtBetween(sensorId, startTime, endTime, pageRequest);
        } else if (sensorId != null && dataTypeId != null) {
            result = sensorDataService.getListBySensorIdAndDataTypeId(sensorId, dataTypeId, pageRequest);
        } else if (sensorId != null) {
            result = sensorDataService.getListBySensorId(sensorId, pageRequest);
        } else if (dataTypeId != null) {
            result = sensorDataService.getListByDataTypeId(dataTypeId, pageRequest);
        } else {
            result = sensorDataService.getList(pageRequest);
        }

        ApiResponse<?> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched sensor data list successfully",
            result
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@RequestBody @Valid SensorDataCreateDTO dto) {
        SensorDataDTO created = sensorDataService.create(dto);

        ApiResponse<SensorDataDTO> response = ApiResponse.success(
            HttpStatus.CREATED,
            "Sensor data created successfully",
            created
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {
        sensorDataService.delete(id);
        ApiResponse<?> response = ApiResponse.success(
            HttpStatus.OK,
            "Sensor data deleted successfully",
            null
        );
        return ResponseEntity.ok(response);
    }
}
