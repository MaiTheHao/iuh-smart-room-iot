package com.example.smartroom.device_management.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.smartroom.common.util.ApiResponse;
import com.example.smartroom.device_management.dto.connection_type.ConnectionTypeCreateDTO;
import com.example.smartroom.device_management.dto.connection_type.ConnectionTypeDTO;
import com.example.smartroom.device_management.service.ConnectionTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/connection-types")
@RequiredArgsConstructor
public class ConnectionTypeControllerV1 {
    private final ConnectionTypeService connectionTypeService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllConnectionTypes(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<ConnectionTypeDTO> connectionTypes = connectionTypeService.getList(pageRequest);

        ApiResponse<?> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched connection types successfully",
            connectionTypes
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getConnectionTypeById(@PathVariable Long id) {
        ConnectionTypeDTO connectionType = connectionTypeService.getConnectionTypeById(id);

        ApiResponse<ConnectionTypeDTO> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched connection type successfully",
            connectionType
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<?>> getConnectionTypeByCode(@PathVariable String code) {
        ConnectionTypeDTO connectionType = connectionTypeService.getConnectionTypeByCode(code);

        ApiResponse<ConnectionTypeDTO> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched connection type by code successfully",
            connectionType
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createConnectionType(@RequestBody @Valid ConnectionTypeCreateDTO dto) {
        ConnectionTypeDTO createdConnectionType = connectionTypeService.createConnectionType(dto);

        ApiResponse<ConnectionTypeDTO> response = ApiResponse.success(
            HttpStatus.CREATED,
            "Connection type created successfully",
            createdConnectionType
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteConnectionType(@PathVariable Long id) {
        ConnectionTypeDTO deletedConnectionType = connectionTypeService.deleteConnectionTypeById(id);

        ApiResponse<ConnectionTypeDTO> response = ApiResponse.success(
            HttpStatus.OK,
            "Connection type deleted successfully",
            deletedConnectionType
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
