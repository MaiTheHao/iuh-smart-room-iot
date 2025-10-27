package com.example.smartroom.device_management.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.smartroom.common.util.ApiResponse;
import com.example.smartroom.device_management.dto.device.DeviceCreateDTO;
import com.example.smartroom.device_management.dto.device.DeviceDTO;
import com.example.smartroom.device_management.service.DeviceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class DeviceControllerV1 {
    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllDevices(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(required = false) String hubId
    ) {
        Pageable pageRequest = PageRequest.of(page, size);

        Page<DeviceDTO> devices = hubId != null
            ? deviceService.getListByHubId(hubId, pageRequest)
            : deviceService.getList(pageRequest);

        ApiResponse<?> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched devices successfully",
            devices
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getDeviceById(@PathVariable String id) {
        DeviceDTO device = deviceService.getDeviceById(id);

        ApiResponse<DeviceDTO> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched device successfully",
            device
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createDevice(@RequestBody @Valid DeviceCreateDTO deviceCreateDto) {
        DeviceDTO createdDevice = deviceService.createDevice(deviceCreateDto);

        ApiResponse<DeviceDTO> response = ApiResponse.success(
            HttpStatus.CREATED,
            "Device created successfully",
            createdDevice
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteDevice(@PathVariable String id) {
        DeviceDTO deletedDevice = deviceService.deleteDeviceById(id);

        ApiResponse<DeviceDTO> response = ApiResponse.success(
            HttpStatus.OK,
            "Device deleted successfully",
            deletedDevice
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
