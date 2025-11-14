package com.example.smartroom.modules.device.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.smartroom.core.common.vo.ApiResponseBody;
import com.example.smartroom.modules.device.dto.CreateDeviceDto;
import com.example.smartroom.modules.device.dto.DeviceDto;
import com.example.smartroom.modules.device.service.DeviceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.device.dto.UpdateDeviceDto;

@RestController
    @RequestMapping("/api/v1/devices")
    @RequiredArgsConstructor
    public class DeviceControllerV1 {
        private final DeviceService deviceService;

        @PostMapping
        public ResponseEntity<ApiResponseBody<DeviceDto>> createDevice(
                @RequestBody @Valid CreateDeviceDto createDeviceDto) {
            DeviceDto deviceDto = deviceService.create(createDeviceDto);
            return ResponseEntity.ok(ApiResponseBody.success("Created successfully", deviceDto));
        }

        @GetMapping
        public ResponseEntity<ApiResponseBody<PaginationInfo<DeviceDto>>> getDevices(
                @PageableDefault(size = 10) Pageable pageable,
                @RequestParam(required = false) String lang) {
            PaginationInfo<DeviceDto> devicePage = deviceService.getList(pageable, lang);
            return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", devicePage));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponseBody<DeviceDto>> getDeviceById(
                @PathVariable Long id,
                @RequestParam(required = false) String lang) {
            DeviceDto deviceDto = deviceService.getById(id, lang);
            return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", deviceDto));
        }

        @GetMapping("/gateways")
        public ResponseEntity<ApiResponseBody<PaginationInfo<DeviceDto>>> getGateways(
                @PageableDefault(size = 10) Pageable pageable,
                @RequestParam(required = false) String lang) {
            PaginationInfo<DeviceDto> gatewayPage = deviceService.getGateways(pageable, lang);
            return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", gatewayPage));
        }

        @GetMapping("/gateways/{gatewayId}/connected-devices")
        public ResponseEntity<ApiResponseBody<PaginationInfo<DeviceDto>>> getDevicesByGatewayId(
                @PathVariable Long gatewayId,
                @PageableDefault(size = 10) Pageable pageable,
                @RequestParam(required = false) String lang) {
            PaginationInfo<DeviceDto> devicePage = deviceService.getByGatewayId(gatewayId, pageable, lang);
            return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", devicePage));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponseBody<DeviceDto>> updateDevice(
                @PathVariable Long id,
                @RequestBody @Valid UpdateDeviceDto updateDeviceDto,
                @RequestParam(required = false) String lang) {
            DeviceDto deviceDto = deviceService.update(id, updateDeviceDto, lang);
            return ResponseEntity.ok(ApiResponseBody.success("Updated successfully", deviceDto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponseBody<Boolean>> deleteDevice(@PathVariable Long id) {
            deviceService.delete(id);
            return ResponseEntity.ok(ApiResponseBody.success("Deleted successfully", true));
        }
    }