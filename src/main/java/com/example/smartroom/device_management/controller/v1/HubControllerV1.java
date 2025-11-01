package com.example.smartroom.device_management.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.smartroom.common.vo.ApiResponse;
import com.example.smartroom.device_management.dto.hub.HubCreateDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;
import com.example.smartroom.device_management.service.HubService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
public class HubControllerV1 {
    private final HubService hubService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllHubs(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(required = false) String roomId
    ) {
        Pageable pageRequest = PageRequest.of(page, size);

        Page<HubDTO> hubs = (roomId != null && !roomId.isBlank())
            ? hubService.getListByRoomId(roomId, pageRequest)
            : hubService.getList(pageRequest);

        ApiResponse<?> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched hubs successfully",
            hubs
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getHubById(@PathVariable String id) {
        HubDTO hub = hubService.getHubById(id);

        ApiResponse<HubDTO> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched hub successfully",
            hub
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createHub(@RequestBody @Valid HubCreateDTO hubCreateDto) {
        HubDTO createdHub = hubService.createHub(hubCreateDto);

        ApiResponse<HubDTO> response = ApiResponse.success(
            HttpStatus.CREATED,
            "Hub created successfully",
            createdHub
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteHub(@PathVariable String id) {
        HubDTO deletedHub = hubService.deleteHubById(id);

        ApiResponse<HubDTO> response = ApiResponse.success(
            HttpStatus.OK,
            "Hub deleted successfully",
            deletedHub
        );
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
